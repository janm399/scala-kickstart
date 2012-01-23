package org.cakesolutions.scala.beans.factory

import tools.nsc.interpreter.IMain
import tools.nsc.Settings
import java.io.ByteArrayOutputStream

/**
 * @author janmachacek
 */
class ScalaBeanFactory(private val beanType: Class[_ <: AnyRef],
                       private val mixinTypes: Seq[Class[_ <: AnyRef]]) {
  val loader = new DynamicClassLoader
  val clazz = loader.buildClass(beanType, mixinTypes)

   def getTypedObject[T] = getObject.asInstanceOf[T]

   def getObject = {
     clazz.newInstance()
   }

   def getObjectType = null

   def isSingleton = true

}

object DynamicClassLoader {
  private var id = 0
  def uniqueId = synchronized {  id += 1; "Klass" + id.toString }
}

class DynamicClassLoader extends java.lang.ClassLoader(getClass.getClassLoader) {

  def buildClass(t: Class[_ <: AnyRef], vs: Seq[Class[_ <: AnyRef]]) = {
    val id = DynamicClassLoader.uniqueId

    val classDef = new StringBuilder

    classDef.append("class ").append(id)
    classDef.append(" extends ").append(t.getCanonicalName)
    vs.foreach(c => classDef.append(" with %s".format(c.getCanonicalName)))

    val settings = new Settings(null)
    settings.usejavacp.value = true
    val interpreter = new IMain(settings)

    interpreter.compileString(classDef.toString())

    val r = interpreter.classLoader.getResourceAsStream(id)
    val o = new ByteArrayOutputStream
    val b = new Array[Byte](16384)
    Stream.continually(r.read(b)).takeWhile(_ > 0).foreach(o.write(b, 0, _))
    val bytes = o.toByteArray

    defineClass(id, bytes, 0, bytes.length)
  }

}


/*
class ScalaBeanFactory(private val beanType: Class[_ <: AnyRef],
                       private val mixinTypes: Seq[Class[_ <: AnyRef]],
                       private val constructorArgs: Array[_ <: AnyRef]) {
  val loader = new DynamicClassLoader
  val clazz = loader.buildClass(beanType, mixinTypes, constructorArgs)

   def getTypedObject[T] = getObject.asInstanceOf[T]

   def getObject = {
     if (constructorArgs.length == 0) {
       clazz.newInstance()
     } else {
       clazz.getConstructors.find(c => c.getParameterTypes.length == constructorArgs.length) match {
         case Some(c) => c.newInstance(constructorArgs:_*)
         case None => throw new RuntimeException("Cannot instantiate " + beanType + " with " + mixinTypes)
       }
     }
   }

   def getObjectType = null

   def isSingleton = true

}

object DynamicClassLoader {
  private var id = 0
  def uniqueId = synchronized {  id += 1; "Klass" + id.toString }
}

class DynamicClassLoader extends java.lang.ClassLoader(getClass.getClassLoader) {

  def buildClass(t: Class[_ <: AnyRef], vs: Seq[Class[_ <: AnyRef]], constructorArgs: Array[_ <: AnyRef]) = {
    val id = DynamicClassLoader.uniqueId

    val classDef = new StringBuilder

    def appendConstructorArgs(decl: Boolean) {
      if (constructorArgs.length > 0) {
        classDef.append("(")
        for (i <- 0 until constructorArgs.length) {
          if (i > 0) classDef.append(",")
          classDef.append("arg" + i)
          if (decl) classDef.append(":").append(constructorArgs(i).getClass.getCanonicalName)
        }
        classDef.append(")")
      }
    }

    classDef.append("class ").append(id)
    appendConstructorArgs(true)
    classDef.append(" extends ").append(t.getCanonicalName)
    appendConstructorArgs(false)
    vs.foreach(c => classDef.append(" with %s".format(c.getCanonicalName)))

    val settings = new Settings(null)
    settings.usejavacp.value = true
    val interpreter = new IMain(settings)

    interpreter.compileString(classDef.toString())

    val r = interpreter.classLoader.getResourceAsStream(id)
    val o = new ByteArrayOutputStream
    val b = new Array[Byte](16384)
    Stream.continually(r.read(b)).takeWhile(_ > 0).foreach(o.write(b, 0, _))
    val bytes = o.toByteArray

    defineClass(id, bytes, 0, bytes.length)
  }

}

*/