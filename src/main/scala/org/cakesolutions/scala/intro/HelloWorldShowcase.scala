package org.cakesolutions.scala.intro

import java.util.Date

/**
 * @author janmachacek
 */
object HelloWorldShowcase {

  def main(args: Array[String]) {
    val Command = "(\\w*)\\W+(\\w*)".r

    def work() {
      readLine() match {
        case Command(verb, what) =>
          (verb.toLowerCase, what.toLowerCase) match {
            case ("say", "hello") => println("Hello")
            case ("give", "time") => println("It is " + new Date)
            case (_, _) => println("What do you mean " + verb + " " + what + "?")
          }
          work()

        case "exit" =>

        case _ => 
          println("Don't know what to do; say exit to quit.")
          work()
      }
    }

    work()
  }

}