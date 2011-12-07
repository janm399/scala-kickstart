package org.cakesolutions.scala.intro

/**
 * @author janmachacek
 */
object Functions {

  class Multiple(howMany: Int) {
    def makeF = (i: Int) => i * howMany
  }
  
  def curry(i: Int)(j: Int) = i * j

  def nonPure = math.rint(100)
  
  def main(args: Array[String]) {
    // val f: Double => Double = (d: Double) => d * d
    val f = (x: Double, y: Double) => x * y
    val fWithXEq5 = f(5, _: Double)
    val oneTimeX = curry(1)_
    
    println(fWithXEq5(3))
    println(curry(1)(2))
    println(oneTimeX(2))
    println(new Multiple(5).makeF(4))
  }
  
}