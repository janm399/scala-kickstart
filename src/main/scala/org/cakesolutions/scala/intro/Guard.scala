package org.cakesolutions.scala.intro

import javax.sql.DataSource

/**
 * @author janmachacek
 */
trait ResourceOps {
  def close()
}

class CloseableResourceOps[R <: {def close()}](val resource: R) extends ResourceOps {
  def close() {resource.close()}
}

object Guard {
  
  implicit def withCloseToCloseableResourceOps[R <: {def close()}](r: R) = new CloseableResourceOps(r)
  
  def guard[T](f: => T) = {
    f
  }
  
  def using[R <% ResourceOps, T](init: => R)(op: R => T) {
    val r = init
    op(r)
    r.close()
  }
  
}

object Resources {
  
  def main(args: Array[String]) {
    
  }
  
  class X(val db1: DataSource, val db2: DataSource) {
    import Guard._
    
    def work() {
      using (db1.getConnection) { c =>
        
      }
      val c1 = guard(db1.getConnection)
    }
    
  }
  
} 