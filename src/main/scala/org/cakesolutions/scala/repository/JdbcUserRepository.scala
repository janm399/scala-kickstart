package org.cakesolutions.scala.repository

import java.sql.ResultSet
import org.springframework.jdbc.core.{BeanPropertyRowMapper, ResultSetExtractor, RowMapper, JdbcTemplate}
import org.springframework.stereotype.Repository
import org.springframework.beans.factory.annotation.Autowired
import org.cakesolutions.scala.domain.User
import scalaz.IterV._
import scalaz.{Input, IterV}

/**
 * @author Jan Machacek
 */
@Repository
class JdbcUserRepository @Autowired() (private val jdbcTemplate: JdbcTemplate)
  extends AutomaticBeanPropertyRowMapper {

  import JdbcTemplateCounterpart._

  /**
   * Following all the magic, the we execute the SQL statement and return Scala
   * ``List[User]``; returned from the ``list[E]`` iteratee
   *
   * @return List of all Users
   */
  def findAll = 
    jdbcTemplate.query("select * from USER", list[User])
}

/**
 * Functions and types for the Scala-ised ``JdbcTemplate`` support. Out of the box, we want
 * to support the syntax of the functions, but we also want to add support for iteratees.
 * 
 * In this initial stab at the implementation, I have the ``list[T]``, ``all[T]``, ``one[T]``
 * iteratees--in addition to the usual ones (``head[T]`` etc.) from Scalaz.
 * 
 * Import the functions in this object in your class that uses the ``JdbcTemplate`` that you
 * wish to bring to the Scala world.
 *
 * @author janmachacek
 */
object JdbcTemplateCounterpart {

  /**
   * Iteratee that collects all values in the underlying iteration, in ``JdbcTemplateOps`` terms,
   * this usually means all rows returned by the <em>select</em> statement.
   * 
   * @tparam E the element type
   * @return ``List[E]`` of all elements
   */
  def list[E] = collect[E, List]

  /**
   * Iteratee that expects exactly one row in the underlying iterator. If there is more than one
   * value, this iteratee throws an exception. Use the ``oneE[E]`` iteratee if you do not want to
   * deal with the exception, but use the ``Either[T, None]`` type.
   * 
   * @tparam E the element type
   * @return ``List[T]`` of all elements
   */
  def one[E]: IterV[E, E] = {
    def step(s: Input[E]): IterV[E, E] =
      s(el = {e =>
          s(el = _ => throw new RuntimeException("Too many results."),
            empty = Done(None, EOF[E]),
            eof = Done(None, EOF[E]))
          Done(e, s)},
        empty = throw new RuntimeException("No results."),
        eof = throw new RuntimeException("No results."))
    Cont(step)
  }
  
  /**
   * Iteratee that expects exactly one row in the underlying iterator, returning:
   * <ul>
   *   <li>left of ``Either[FailedIteratee, E]`` when there was no result or more than one result,</li>
   *   <li>right of ``Either[FailedIteratee, E]`` when there was exactly one result</li>
   * </ul>
   * You may examine the type of the left projection to find out what is the cause of the failure.
   * The left projection will hold either ``TooManyResults()`` or ``NoResults()`` instances.
   *
   * @tparam E the element type
   * @return ``Either[FailedIteratee, E]``: the left projection on failure, the right projection on
   *  success
   */
  def oneE[E]: IterV[E, Either[FailedIteratee, E]] = {
    def step(s: Input[E]): IterV[E, Either[FailedIteratee, E]] =
      s(el = {e =>
          s(el = _ => Done(Left(TooManyResults()), EOF[E]),
            empty = Done(Left(NoResults()), EOF[E]),
            eof = Done(Left(NoResults()), EOF[E]))
          Done(Right(e), s)},
        empty = Done(Left(NoResults()), s),
        eof = Done(Left(NoResults()), s))
    
    Cont(step)
  }

  /**
   * Simple hierarchy of iteratee failures for the ``Either`` projection
   */
  abstract case class FailedIteratee()
  case class TooManyResults() extends FailedIteratee
  case class NoResults() extends FailedIteratee

  /**
   * The 'magic' that implicitly converts the Spring Framework's ``JdbcTemplate`` to our
   * ``JdbcTemplateOps``
   *
   * @param jdbcTemplate the ``JdbcTemplate`` instance to convert
   * @return the Scala counterpart
   */
  implicit def toJdbcTempklateOps(jdbcTemplate: JdbcTemplate): JdbcTemplateCounterpart =
    new JdbcTemplateCounterpart(jdbcTemplate)

}

/**
 *
 *
 * @author Jan Machacek
 */
trait AutomaticBeanPropertyRowMapper {
 
  implicit def getAutomaticRowMapper[T](implicit evidence: ClassManifest[T]): RowMapper[T] =
    // ``erasure`` gives the erased type signarue--hence the cast to ``RowMapper[T]``
    BeanPropertyRowMapper.newInstance(evidence.erasure).asInstanceOf[RowMapper[T]]
  
}

class JdbcTemplateCounterpart(private val jdbcTemplate: JdbcTemplate) {

  import scalaz.Enumerator
  import scalaz.IterV._
  import scalaz.IterV

  class ResultSetIterator[T](private val resultSet: ResultSet,
                             private val mapper: RowMapper[T]) extends Iterator[T] {

    def hasNext = !resultSet.isLast

    def next() = {
      resultSet.next()
      // TODO: Replace 0 with the actual row number, deal with mutable state
      mapper.mapRow(resultSet, 0)
    }

    def close() {
      println("closed")
      resultSet.close()
    }
  }

  implicit val resultSetEnumerator = new Enumerator[ResultSetIterator] {

    @scala.annotation.tailrec
    def apply[E, A](iterator: ResultSetIterator[E], i: IterV[E, A]): IterV[E, A] = i match {
      case _ if !iterator.hasNext =>
        iterator.close()
        i
      case Done(acc, input) =>
        iterator.close()
        i
      case Cont(k) =>
        val x = iterator.next()
        apply(iterator, k(El(x)))
    }
  }

  def query[R, T](sql: String, i: IterV[T, R])(implicit rowMapper: RowMapper[T]): R = {
    jdbcTemplate.query(sql, new ResultSetExtractor[R] {
      def extractData(rs: ResultSet) = {
        val iterator = new ResultSetIterator[T](rs, rowMapper)
        i(iterator).run
      }
    })
  }

}