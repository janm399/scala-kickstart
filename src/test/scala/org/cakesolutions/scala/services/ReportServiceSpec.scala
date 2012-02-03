package org.cakesolutions.scala.services

import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.specs2.spring.Specification
import org.springframework.test.context.ContextConfiguration

/**
 * -javaagent:/Users/janmachacek/.m2/repository/org/springframework/spring-instrument/3.1.0.RELEASE/spring-instrument-3.1.0.RELEASE.jar
 *
 * @author janmachacek
 */
@ContextConfiguration(Array("classpath*:/META-INF/spring/module-context.xml"))
class ReportServiceSpec extends Specification {
  @Autowired implicit var sessionFactory: SessionFactory = _
  @Autowired var reportService: ReportService = _

  "generatess and then accesses and combines the reports" in {
    reportService.produce()
    reportService.combine()

    reportService.get(102L).getFileName() must_== ("out.txt")
  }
}
