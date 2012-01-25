package org.cakesolutions.scala.web

import org.springframework.stereotype.Controller
import org.cakesolutions.scala.services.EntityService
import org.springframework.beans.factory.annotation.Autowired
import org.cakesolutions.scala.domain.{Page, User}
import org.springframework.web.bind.annotation.{ModelAttribute, RequestMethod, RequestMapping}

/**
 * @author janmachacek
 */
@Controller
class HomeController @Autowired() (private val entityService: EntityService) {
  implicit val everything = Page(0, Int.MaxValue)

  @RequestMapping(value = Array("/users"), method = Array(RequestMethod.GET))
  @ModelAttribute
  @ViewName("users")
  def users = entityService.find[User]

  @RequestMapping(value = Array("/index.html"), method = Array(RequestMethod.GET))
  @ModelAttribute
  @ViewName("home")
  def x = "home"
}
