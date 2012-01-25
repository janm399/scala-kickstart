package org.cakesolutions.scala.web

import org.springframework.stereotype.Controller
import org.cakesolutions.scala.services.EntityService
import org.springframework.beans.factory.annotation.Autowired
import org.cakesolutions.scala.domain.{Page, User}
import org.springframework.web.bind.annotation.{ModelAttribute, RequestMethod, RequestMapping}
import org.springframework.validation.BindingResult
import org.springframework.ui.Model
import javax.validation.Valid

/**
 * @author janmachacek
 */
@Controller
@RequestMapping(Array("/users"))
class UserController @Autowired() (private val entityService: EntityService) {
  implicit val everything = Page(0, Int.MaxValue)

  @RequestMapping(value = Array("/index"), method = Array(RequestMethod.GET))
  def index(model: Model) = {
    model.addAttribute(entityService.find[User])
    "users/index"
  }

  @RequestMapping(value = Array("/add"), method = Array(RequestMethod.GET))
  def add(model: Model) = {
    model.addAttribute(new User())
    "users/edit"
  }

  @RequestMapping(value = Array("/index"), method = Array(RequestMethod.POST))
  def save(@Valid @ModelAttribute user: User, result: BindingResult) = {
    if (result.hasErrors) {
      "users/edit"
    } else {
      entityService.save(user)
      "redirect:/users/index.html"
    }
  }
}
