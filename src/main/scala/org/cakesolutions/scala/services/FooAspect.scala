package org.cakesolutions.scala.services

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.{Before, Aspect}

/**
 * @author janmachacek
 */
@Aspect
class FooAspect {

  @Before("execution(* org.cakesolutions.scala..*.*(..))")
  def foo(jp: JoinPoint) {
    println("foo " + jp)
  }

}
