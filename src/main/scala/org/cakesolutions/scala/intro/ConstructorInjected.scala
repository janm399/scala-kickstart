package org.cakesolutions.scala.intro

import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author janmachacek
 */
@Component
class ConstructorInjected @Autowired() (private val dependency: Dependency) {

}
