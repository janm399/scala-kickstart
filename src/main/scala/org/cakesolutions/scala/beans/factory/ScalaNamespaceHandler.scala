package org.cakesolutions.scala.beans.factory

import org.springframework.beans.factory.xml.NamespaceHandlerSupport

/**
 * @author janmachacek
 */
class ScalaNamespaceHandler extends NamespaceHandlerSupport {
  def init() {
    // registerBeanDefinitionParser("property-placeholder", new PropertyPlaceholderBeanDefinitionParser)
  }
}

/*

<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns:context="http://www.springframework.org/schema/context"
  xmlns:scala="http://www.springframework.org/schema/scala"
  xsi:schemaLocation="...">

  <context:load-time-weaver aspectj-weaving="on" />
  <context:component-scan base-package="org.cakesolutions.scala.services"/>

  <scala:bean class="EntityService">
    <scala:with trait="Sha1PasswordCodec" if="#{environment['profile'] == 'live'}"/>
    <scala:with trait="PlainPassowordCodec" if=#{environment['profile'] != 'live'}/>
    <scala:with trait="EmailNotifier" />
  </scala:bean>

</beans>

*/