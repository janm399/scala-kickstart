package org.cakesolutions.scala.intro

/**
 * @author janmachacek
 */

object Immutability {

  class Container(val x: Int)
  
  def main(args: Array[String]) {
    var i = 1
    val j = 1
    i = 2
    // j = 3

    val ii = Set(1, 2, 3)
    //ii += 5
    val mi = collection.mutable.Set(1, 2, 3)
    mi += 5

    val c1 = new Container(1)
    val c2 = new Container(2)
    val containers = List(c1)
    //containers(0) = c2
    
    println(containers(0).x)
  }
  
}