package org.cakesolutions.scala.intro

import org.cakesolutions.scala.domain.Report
import java.io.File
import io.Source

/**
 * @author janmachacek
 */

object InOut {
  type Data = Array[Char]
  import scalaz._
  import scalaz.effects._
  import Scalaz._

  def main(args: Array[String]) {
    val in1 = Report()
    in1.fileName = "x.txt"
    val in2 = new Report()
    in2.fileName = "y.txt"
    
    val out = new Report()
    out.fileName = "out.txt"

    def reportToFile(report: Report) = io { new File("/Users/janmachacek/Tmp/%s".format(report.fileName)) }
    def fileToData(file: File) = io { Source.fromFile(file).toArray }
    def dataToFile(data: IO[Data])(file: File) = io { println("Written " + data.unsafePerformIO.toList + " to " + file) }

    def combine(data: (Data, Data)) = io { data._1 ++ data._2 }

    val x = (reportToFile(in1) >>= fileToData) <|*|> (reportToFile(in2) >>= fileToData) >>= combine
    val y = reportToFile(out) >>= dataToFile(x)

    y.unsafePerformIO
  }
  
}
