package org.cakesolutions.scala.beans.factory

import org.springframework.beans.factory.xml.NamespaceHandlerSupport
import org.springframework.context.config.PropertyPlaceholderBeanDefinitionParser

/**
 * @author janmachacek
 */
class ScalaNamespaceHandler extends NamespaceHandlerSupport {
  def init() {
    // registerBeanDefinitionParser("property-placeholder", new PropertyPlaceholderBeanDefinitionParser)
  }
}
