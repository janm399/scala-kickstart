package org.cakesolutions.scala.intro

import com.sun.speech.freetts.VoiceManager

/**
 * @author janmachacek
 */
object Composition {

  trait Speakable {

    def sound: String

    def speak() {
      val voice = VoiceManager.getInstance().getVoice("kevin16")
      voice.allocate()
      voice.speak(sound)
      voice.deallocate()
    }

  }

  abstract class Animal {
    def sound: String
  }
  class Dog extends Animal {
    def sound = "Woof"
  }
  class Cat extends Animal {
    def sound = "Meow"
  }
  class SpeakingCat extends Cat with Speakable 
  
  def main(args: Array[String]) {
    val cat = new Cat with Speakable
    val dog = new Dog with Speakable
    val muteDog = new Dog
    val speakingCat = new SpeakingCat

    cat.speak()
    dog.speak()
    speakingCat.speak()
    // muteDog.speak()
  }

}