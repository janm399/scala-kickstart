package org.cakesolutions.scala.services

import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.specs2.spring.{HibernateDataAccess, BeanTables, Specification}
import org.springframework.test.context.ContextConfiguration
import org.cakesolutions.scala.domain.{Page, User}
import org.specs2.spring.annotation.DataSource
import org.hsqldb.jdbc.JDBCDriver
import org.specs2.execute.Result

/**
 * -javaagent:/Users/janmachacek/.m2/repository/org/springframework/spring-instrument/3.1.0.RELEASE/spring-instrument-3.1.0.RELEASE.jar
 *
 * @author janmachacek
 */
@ContextConfiguration(Array("classpath*:/META-INF/spring/module-context.xml"))
class EntityServiceSpec extends Specification with BeanTables with HibernateDataAccess {
  @Autowired implicit var sessionFactory: SessionFactory = _
  @Autowired var entityService: EntityService = _

  "find locates the inserted objects" in {
    "username" | "firstName" | "lastName" |
     "janm"   !! "Jan"       ! "Machacek" |
     "marco"  !! "Marc"      ! "Owen"     |> {
      u: User =>
      println(u)
      success
    }

    val rows =
    "username" | "firstName" | "lastName" |
     "janm"   !! "Jan"       ! "Machacek" |
     "marco"  !! "Marc"      ! "Owen"     |< (classOf[User])

    val m: List[Result] = rows.map { u => success.asInstanceOf[Result] }
    m.fold(success){(r1: Result, r2: Result) => r2 }

    "username" | "firstName" | "lastName" |
     "janm"   !! "Jan"       ! "Machacek" |
     "marco"  !! "Marc"      ! "Owen"     |> insert[User]
    
    entityService.get[User](1L) must_== (new User() {username = "janm"; firstName = "Jan"; lastName = "Machacek"})
    entityService.get[User](2L) must_== (new User() {username = "marco"; firstName = "Marc"; lastName = "Owen"})
  }

  "loads, saves and deletes" in {
    val user = new User()
    user.username = "janm"
    user.firstName = "foo"
    user.lastName = "bar"

    entityService.save(user)
    entityService.get[User](user.getId()) must_== (user)
  }
}
