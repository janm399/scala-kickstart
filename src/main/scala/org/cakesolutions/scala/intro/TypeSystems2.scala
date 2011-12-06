package org.cakesolutions.scala.intro

import java.util.Random
import java.io.File

/**
 * @author janmachacek
 */

object TypeSystems2 {

  trait GetterLike[E] {
    def get: Seq[E]
  }

  class HardcodedSource(items: Seq[Int]) extends GetterLike[Int] {
    def get = items
  }

  class RandomSource(howMany: Int) extends GetterLike[Int] {
    val random = new Random
    
    def get = for (i <- 0 to howMany) yield random.nextInt() 
  }
  
  class FileSource(file: File) {
    def get = Nil
  }
  
  def sumAll(source: GetterLike[Int]) = source.get.sum
  def sumAll[S <: {def get: Seq[Int]}](source: S) = source.get.sum
  
  def main(args: Array[String]) {
    val hs = sumAll(new HardcodedSource(List(1, 2, 3, 4, 5, 6, 7, 8, 9)))
    val rs = sumAll(new RandomSource(10))
    
    println(hs)
    println(rs)
  }
  
}