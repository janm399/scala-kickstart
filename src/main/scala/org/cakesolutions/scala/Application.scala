package org.cakesolutions.scala

import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import services.{SmsNotifier, PlainPasswordCodec, UserService}
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.transaction.annotation.EnableTransactionManagement

/**
 * @author janmachacek
 */
@Configuration
@EnableTransactionManagement
class Application {
  @Autowired var sessionFactory: SessionFactory = _

  @Bean
  def userService = new UserService(sessionFactory)
    with PlainPasswordCodec
    with SmsNotifier

}
