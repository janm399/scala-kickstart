package org.cakesolutions.scala.intro

/**
 * @author janmachacek
 */

object SSolver1 {

  def solve(f: Double => Double): Double = {
    val h = 0.001
    def diff(x: Double) = (f(x + h) - f(x)) / h
    var x = -100000.0
    val maxCount = 1000
    for (i <- 0 until maxCount) {
      x = x - f(x) / diff(x)
      if (math.abs(f(x)) < h) return x
    }
    
    0
  }

  def main(args: Array[String]) {
    val x = solve(x => x * x + x - 2)
    println(x)
  }

}