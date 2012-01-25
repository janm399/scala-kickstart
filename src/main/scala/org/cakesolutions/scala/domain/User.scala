package org.cakesolutions.scala.domain

import reflect.BeanProperty
import javax.persistence.{Version, GeneratedValue, Id, Entity}
import javax.validation.constraints.Size


/**
 * @author janmachacek
 */
@Entity
case class User() {
  @Id
  @GeneratedValue
  @BeanProperty
  var id: Long = _
  @Version
  @BeanProperty
  var version: Int = _
  @BeanProperty
  @Size(min = 2, max = 50)
  var username: String = _
  @BeanProperty
  @Size(min = 2, max = 50)
  var firstName: String = _
  @BeanProperty
  @Size(min = 2, max = 50)
  var lastName: String = _

}
