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

  private def combine = {
    val reports = 
      sessionFactory.getCurrentSession.createCriteria(classOf[Report]).list().asInstanceOf[java.util.List[Report]].toList

    val out = new Report()
    out.fileName = "out.txt"

    def reportToFile(report: Report) = io { new File("/Users/janmachacek/Tmp/%s".format(report.fileName)) }
    def fileToData(file: File) = io { Source.fromFile(file).toArray }
    def dataToFile(data: IO[Data])(file: File) = io { println("Written " + data.unsafePerformIO.toList + " to " + file) }

    def combine(data: (Data, Data)) = io { data._1 ++ data._2 }

    reports.foldl1 { (r1: Report, r2: Report) =>
      (reportToFile(out) >>= dataToFile(
        (reportToFile(r1) >>= fileToData) <|*|> (reportToFile(r2) >>= fileToData) >>= combine)
      ).unsafePerformIO

      out
    }
  }
  
  @Transactional
  def combineReports = {
    val report = Report()
    report.fileName = "x.txt"

    def reportToFile(report: Report) = io { new File("/Users/janmachacek/Tmp/%s".format(report.fileName)) }
    def fileToData(file: File) = io { Source.fromFile(file).toArray }
    def dataToFile(file: File)(data: IO[Data]) = io { println("Written to " + file); data.unsafePerformIO }
    def dataToReport(report: Report)(a: Data) = io { reportToFile(report) }

    //val x = (reportToFile(report) >>= fileToData >>= dataToReport(report)) // <**> (fileForReport(b) >>= bytesForFile >>= ioToReport)
    val x = reportToFile(report) >>= fileToData
    val f = x.unsafePerformIO
    println(f)
  }

}
