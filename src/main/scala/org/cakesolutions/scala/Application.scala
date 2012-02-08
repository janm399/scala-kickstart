package org.cakesolutions.scala

import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import services.{SmsNotifier, PlainPasswordCodec, UserService}
import org.springframework.context.annotation.{Bean, Configuration}

/**
 * @author janmachacek
 */
@Configuration
class Application {
  @Autowired var sessionFactory: SessionFactory = _

  @Bean
  def userService = new UserService(sessionFactory)
    with PlainPasswordCodec
    with SmsNotifier

}
