package org.cakesolutions.scala.services

import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.specs2.spring.{HibernateDataAccess, BeanTables, Specification}
import org.springframework.test.context.ContextConfiguration
import org.cakesolutions.scala.domain.{Page, User}


/**
 * @author janmachacek
 */
@ContextConfiguration(Array("classpath*:/META-INF/spring/module-context.xml"))
class EntityServiceSpec extends Specification with BeanTables with HibernateDataAccess {
  @Autowired implicit var sessionFactory: SessionFactory = _
  @Autowired var entityService: EntityService = _
  implicit var page = Page(0, 100)
  
  "find locates the inserted objects" in {
    "username" | "firstName" | "lastName" |
     "janm"   !! "Jan"       ! "Machacek" |
     "marco"  !! "Marc"      ! "Owen"     |> insert[User]
    
    entityService.find[User].size() must_==(2)
  }

}
