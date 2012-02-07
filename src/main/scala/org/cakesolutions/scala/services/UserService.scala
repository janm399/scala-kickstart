package org.cakesolutions.scala.services

import org.cakesolutions.scala.domain.User
import org.springframework.transaction.annotation.Transactional
import org.hibernate.SessionFactory

trait PasswordCodec {
  def encode(plainText: String): String
}

trait Notifier {
  def registered(user: User)
}

trait Sha1PasswordCodec extends PasswordCodec {
  def encode(plainText: String) = "SHA1 " + plainText
}

trait PlainPasswordCodec extends PasswordCodec {
  def encode(plainText: String) = plainText
}

trait EmailNotifier extends Notifier {
  def registered(user: User) { /* send e-mail */ }
}

trait SmsNotifier extends Notifier {
  def registered(user: User) { /* send SMS */ }
}

class UserService (private val sessionFactory: SessionFactory) {
  this: PasswordCodec with Notifier =>

  @Transactional
  def register(user: User) {
    user.password = encode(user.password)
    registered(user)

    sessionFactory.getCurrentSession.saveOrUpdate(user)
  }

}
