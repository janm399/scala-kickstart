package org.cakesolutions.scala.intro

import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author janmachacek
 */
@Component
class SetterInjected {
  private var dependency: Dependency = _

  @Autowired
  def setDependency(dependency: Dependency) {
    this.dependency = dependency;
  }
}
