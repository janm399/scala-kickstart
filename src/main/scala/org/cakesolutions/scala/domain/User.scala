package org.cakesolutions.scala.domain

import reflect.BeanProperty
import javax.persistence.{Version, GeneratedValue, Id, Entity}


/**
 * @author janmachacek
 */
@Entity
class User {
  @Id
  @GeneratedValue
  @BeanProperty
  var id: Long = _
  @Version
  @BeanProperty
  var version: Int = _
  var username: String = _
  var firstName: String = _
  var lastName: String = _

}
