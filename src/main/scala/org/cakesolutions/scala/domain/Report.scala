package org.cakesolutions.scala.domain

import reflect.BeanProperty
import javax.persistence.{Version, GeneratedValue, Id, Entity}


/**
 * @author janmachacek
 */
@Entity
case class Report() {
  @Id
  @GeneratedValue
  @BeanProperty
  var id: Long = _
  @Version
  @BeanProperty
  var version: Int = _
  @BeanProperty
  var fileName: String = _

}
