package org.cakesolutions.scala.web

import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.specs2.spring.{BeanTables, HibernateDataAccess}
import org.specs2.spring.web.{Xhtml, WebContextConfiguration, Specification}

/**
 * -javaagent:/Users/janmachacek/.m2/repository/org/springframework/spring-instrument/3.1.0.RELEASE/spring-instrument-3.1.0.RELEASE.jar
 *
 * @author janmachacek
 */
@WebContextConfiguration(
  contextLocations = Array("classpath*:/META-INF/spring/module-context.xml"),
  webContextLocations = Array("/WEB-INF/springscala-servlet.xml")
)
class UserControllerSpec extends Specification with BeanTables with HibernateDataAccess {
  @Autowired implicit var sessionFactory: SessionFactory = _

  "adding a user should then list it" in {
    val add = Xhtml(get)("/users/add")
    add.body << ("#username", "janm")
    add.body << ("#firstName", "Jan")
    add.body << ("#lastName", "Machacek")
    
    Xhtml(post)(add.body)
    
    val edit = Xhtml(get)("/users/1")

    edit.body >>! "#firstName" must_== ("Jan")
  }
  
}
