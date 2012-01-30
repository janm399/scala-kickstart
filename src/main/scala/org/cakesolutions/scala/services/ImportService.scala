package org.cakesolutions.scala.services

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent._
import scalaz.concurrent._
import scalaz.Scalaz._

/**
 * @author janmachacek
 */
@Service
class ImportService {

  @Transactional
  def importUsers = {

    val cutoff = 30
    implicit val pool = Executors.newFixedThreadPool(5)
    implicit val s = Strategy.Executor

    val out = actor {(xs: Stream[Int]) =>
      xs.zipWithIndex.foreach(p => println("n=" + (p._2 + 1) + " => " + p._1))
      pool.shutdown
    }

    def seqFib(n: Int): Int = if (n < 2) n else seqFib(n - 1) + seqFib(n - 2)

    def fib(n: Int): Promise[Int] =
      if (n < cutoff)
        promise(seqFib(n))
      else
        fib(n - 1).<**>(fib(n - 2))(_ + _)

    Stream.range(1, 41).traverse(fib) to out
  }

}
