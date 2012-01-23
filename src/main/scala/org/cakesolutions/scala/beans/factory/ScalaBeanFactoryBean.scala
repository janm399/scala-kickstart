package org.cakesolutions.scala.beans.factory

import org.springframework.beans.factory.FactoryBean
import tools.nsc.interpreter.IMain
import tools.nsc.Settings
import java.io.ByteArrayOutputStream

/**
 * @author janmachacek
 */
class ScalaBeanFactoryBean extends FactoryBean[AnyRef] {
  private var beanType: Class[_ <: AnyRef] = _
  private var mixinTypes: Seq[Class[_ <: AnyRef]] = Seq()
  
  def setBeanType(beanType: Class[_ <: AnyRef]) {
    this.beanType = beanType
  }
  
  def setMixinTypes(mixinTypes: Seq[Class[_ <: AnyRef]]) {
    this.mixinTypes = mixinTypes
  }
  
  def addMixinType(mixinType: Class[_ <: AnyRef]) {
    this.mixinTypes = this.mixinTypes :+ mixinType
  }

  def getTypedObject[T] = getObject.asInstanceOf[T]
  
  def getObject = {
    val loader = new DynamicClassLoader
    loader.buildClass(beanType, mixinTypes).newInstance().asInstanceOf[AnyRef]
  }

  def getObjectType = null

  def isSingleton = true

  object DynamicClassLoader {
    private var id = 0
    def uniqueId = synchronized {  id += 1; "Klass" + id.toString }
  }

  class DynamicClassLoader extends java.lang.ClassLoader(getClass.getClassLoader) {
    def buildClass(t: Class[_ <: AnyRef], vs: Seq[Class[_ <: AnyRef]]) = {

      // Create a unique ID
      val id = DynamicClassLoader.uniqueId

      // what's the Scala code we need to generate this class?
      val classDef = new StringBuilder
      classDef.append("class %s extends %s".format(id, t.getCanonicalName))
      vs.foreach(c => classDef.append(" with %s".format(c.getCanonicalName)))

      // fire up a new Scala interpreter/compiler
      val settings = new Settings(null)
      settings.usejavacp.value = true
      val interpreter = new IMain(settings)

      // define this class
      interpreter.compileString(classDef.toString())
      // interpreter.compileAndSaveRun("<anon>", classDef)

      // get the bytecode for this new class
      val r = interpreter.classLoader.getResourceAsStream(id)

      val o = new ByteArrayOutputStream
      val b = new Array[Byte](16384)

      Stream.continually(r.read(b)).takeWhile(_ > 0).foreach(o.write(b, 0, _))

      val bytes = o.toByteArray

      // define the bytecode using this classloader; cast it to what we expect
      defineClass(id, bytes, 0, bytes.length)
    }

  }
}
