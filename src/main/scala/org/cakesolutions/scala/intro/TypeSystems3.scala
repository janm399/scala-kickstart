package org.cakesolutions.scala.intro

import collection.immutable.Stack
import org.cakesolutions.scala.intro.TypeSystems3.Cat


/**
 * @author janmachacek
 */

object TypeSystems3 {

  abstract class Animal
  class Cat extends Animal
  class Dog extends Animal
  
  class Stack[+T](items: Seq[T]) {
    def this() = this(Nil)
    def push[B >: T](item: B) = new Stack[B](item +: items)
  }
  
  class S[-T]
  
  def main(args: Array[String]) {
    val animals = new Stack().push(new Cat)
    
    val s1: Stack[Animal] = animals
    val s2: Stack[Any] = animals
  }
  
}