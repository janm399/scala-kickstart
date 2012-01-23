package org.cakesolutions.scala.services

import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import org.hibernate.SessionFactory
import org.cakesolutions.scala.domain.Page
import org.springframework.transaction.annotation.Transactional


/**
 * @author janmachacek
 */
@Service
class EntityService @Autowired() (private val sessionFactory: SessionFactory) {

  @Transactional
  def get[T](id: Serializable)(implicit evidence: ClassManifest[T]) =
    sessionFactory.getCurrentSession.get(evidence.erasure, id).asInstanceOf[T]

  @Transactional
  def find[T](implicit evidence: ClassManifest[T], page: Page) = {
    val criteria = sessionFactory.getCurrentSession.createCriteria(evidence.erasure)
    
    criteria.setFirstResult(page.firstRow)
    criteria.setMaxResults(page.maxRows)
    criteria.list().asInstanceOf[java.util.List[T]]
  }

  @Transactional
  def save(entity: AnyRef) { sessionFactory.getCurrentSession.saveOrUpdate(entity) }

}
