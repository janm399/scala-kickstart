package org.cakesolutions.scala.services

import org.springframework.context.support.ClassPathXmlApplicationContext
import org.cakesolutions.scala.domain.{Page, User}


/**
 * @author janmachacek
 */
object Main {
  
  def main(args: Array[String]) {
    implicit val page = Page(0, 100)

    val ac = new ClassPathXmlApplicationContext("classpath*:/META-INF/spring/module-context.xml")
    val es = ac.getBean(classOf[EntityService])
    
    es.find[User]
  }
  
}
