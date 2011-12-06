package org.cakesolutions.scala.intro

/**
 * @author janmachacek
 */

object TypeSystems3 {

  abstract class Animal {
    type SuitableFood <: Food
  }
  class Cat extends Animal {
    type SuitableFood = CatFood
  }
  class SpeakingCat extends Cat
  
  abstract class Food
  class CatFood extends Food
  class LuxuryCatFood extends CatFood
  class DogFood extends Food
  
  class Zoo[+A](animals: Seq[A]) {
    def this() = this(Nil)
    def add[B >: A](animal: B) = new Zoo[B](animal +: animals)
  }
  
  class Feeder[A <: Animal](animal: A) {
    def feed[F <: A#SuitableFood](food: F) =
      "Fed " + food + " to " + animal
  }
  
  def main(args: Array[String]) {
    val cats = new Zoo().add(new Cat)

    val s1: Zoo[Cat] = cats
    // val s2: Zoo[SpeakingCat] = cats
    val s3: Zoo[Animal] = cats
    val s4: Zoo[Any] = cats

    new Feeder(new Cat).feed(new CatFood)
    new Feeder(new Cat).feed(new LuxuryCatFood)
    // new Feeder(new SpeakingCat).feed(new DogFood)
  }
  
}