package org.cakesolutions.scala.intro

import org.cakesolutions.scala.intro.PatternMatch.{Bar, Foo}


/**
 * @author janmachacek
 */

object PatternMatch {

  case class Foo(id: Int, firstName: String, lastName: String)
  
  class Bar

  def main(args: Array[String]) {
    def work(value: Any) {
      value match {
        case Foo(_, "Ani", _) => println("Hello, Ani")
        case Foo(_, firstName, "Machacek") if firstName != "Jan" => println("There is only one Machacek!")
        case s: String => println("Got some String")
        case b: Bar => println("Some Bar instance")
        case 42 => println("I know what you're doing here!")
        case ("1", 'two) => println("Tuple")
      }
    }

    def work2(value: Any) = 
      value match {
        case Foo(_, "Ani", _) => "x"
        case 42 => 42
      }
    

    work(Foo(1, "Ani", "Chakraborty"))
    work(Foo(2, "Impostor", "Machacek"))
    work("Jan Machacek")
    work(new Bar)
    work(42)
    work(("1", 'two))
    
    val x = work2(42)
    println(x)
  }

}