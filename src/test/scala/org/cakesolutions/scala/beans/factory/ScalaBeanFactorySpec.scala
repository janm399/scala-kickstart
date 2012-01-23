package org.cakesolutions.scala.beans.factory

import org.specs2.mutable.Specification

/**
 * @author janmachacek
 */
class ScalaBeanFactorySpec extends Specification {

  "build" in {
    val f1 = new ScalaBeanFactory(classOf[Cat], Seq(classOf[Speaking], classOf[Eating]))
//    val f2 = new ScalaBeanFactory(classOf[CCat], Seq(classOf[Speaking], classOf[Eating]), Array("x"))

    val c1 = f1.getTypedObject[Cat with Eating with Speaking]
//    val c2 = f2.getTypedObject[CCat with Eating with Speaking]

    c1.isInstanceOf[Cat with Eating with Speaking] must_==(true)
  }

}
