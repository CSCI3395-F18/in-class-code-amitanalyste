package scalaintro

import scala.io.Source
import swiftvis2.plotting.Plot
import swiftvis2.plotting.renderer.SwingRenderer

case class TempData(day:Int, doy: Int, month: Int, year: Int, precip: Double, tave: Double, tmax: Double, tmin: Double)

object TempData extends App {
	def parseLine(line: String): TempData = {
		val p = line.split(",")
		TempData(p(0).toInt, p(1).toInt, p(2).toInt, p(4).toInt, p(5).toDouble,
			p(6).toDouble, p(7).toDouble, p(8).toDouble)
	}

	val source = Source.fromFile("data/SanAntonioTemps.csv")
	val lines = source.getLines.drop(2)
	val data = lines.map(parseLine).toArray
	source.close()

	val hotDay = data.maxBy(_.tmax)
	println(hotDay)
	val wetDay = data.maxBy(_.precip)
	println(wetDay)
	val fracWet = data.count(_.precip >= 1.0).toDouble/data.length
	println(fracWet)
	println(data.map(_.tmax).sum/data.length)
	val rainyDays = data.filter(_.precip > 0.0)
	val reallyRainyDays = data.filter(_.precip >= 1.0)
	val averageTempOnRainyDays = rainyDays.foldLeft(0.0)((acc, td) => acc+td.tmax)/rainyDays.length
	val averageTempOnReallyRainyDays = reallyRainyDays.foldLeft(0.0)((acc, td) => acc+td.tmax)/reallyRainyDays.length
	println(averageTempOnRainyDays)
	println(averageTempOnReallyRainyDays)
	val monthAverages = data.groupBy(_.month).mapValues(days => days.map(_.tmax).sum/days.size)
	monthAverages.toSeq.sortBy(_._1).foreach(println)
	val monthMedians = data.groupBy(_.month).mapValues { days =>
	  val sortedDays = days.sortBy(_.tmax)
	  sortedDays(days.length/2).tmax
	}
	monthMedians.toSeq.sortBy(_._1).foreach(println)
	
	val plot = Plot.scatterPlot(data.map(_.doy), data.map(_.tmax))
	SwingRenderer(plot)
}
