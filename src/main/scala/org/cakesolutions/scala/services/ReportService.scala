package org.cakesolutions.scala.services

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.cakesolutions.scala.domain.Report
import io.Source
import java.io.File

/**
 * @author janmachacek
 */
@Service
class ReportService @Autowired() (private val sessionFactory: SessionFactory) {

  type Data = Array[Char]
  import scala.collection.JavaConversions._
  import scalaz._
  import scalaz.effects._
  import Scalaz._

  def reportToFile(report: Report) = io {
    val file = new File("/Users/janmachacek/Tmp/r/%s".format(report.fileName))
    if (!file.exists()) file.createNewFile()
    file
  }
  def fileToData(file: File) = io { Source.fromFile(file).toArray }
  def dataToFile(data: IO[Data])(file: File) = io {
    println("Written " + new String(data.unsafePerformIO) + " to " + file)
  }

  @Transactional(readOnly = true)
  def get(id: Long) = {
    sessionFactory.getCurrentSession.get(classOf[Report], id).asInstanceOf[Report]
  }

  @Transactional
  def produce() {
    for (i <- 0 to 100) {
      val report = Report()
      val content = "Report %d".format(i)
      report.fileName = "%d".format(i)
      
      (reportToFile(report) >>= dataToFile(io {content.toCharArray})).unsafePerformIO

      sessionFactory.getCurrentSession.saveOrUpdate(report)
    }
  }

  @Transactional
  def combine() {
    val reports = 
      sessionFactory.getCurrentSession.createCriteria(classOf[Report]).list().asInstanceOf[java.util.List[Report]].toList

    val out = Report()
    out.fileName = "out.txt"

    def combine(data: (Data, Data)) = io { data._1 ++ data._2 }

    reports.foldl1 { (r1: Report, r2: Report) =>
      (reportToFile(out) >>= dataToFile(
        (reportToFile(r1) >>= fileToData) <|*|> (reportToFile(r2) >>= fileToData) >>= combine)
      ).unsafePerformIO

      out
    }
    
    sessionFactory.getCurrentSession.saveOrUpdate(out)
  }
  
}
