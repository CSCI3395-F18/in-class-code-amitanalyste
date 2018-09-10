package scalaintro

object TestTemps extends App {
  case class Station(id: String, lat: Double, lon: Double, state: String, name: String)
  val stations = scala.io.Source.fromFile("data/ghcn-daily/ghcnd-stations.txt").getLines.map { line =>
    val id = line.substring(0, 11).trim
    val lat = line.substring(12, 20).trim.toDouble
    val lon = line.substring(21, 30).trim.toDouble
    val state = line.substring(38, 40).trim
    val name = line.substring(41, 71).trim
    Station(id, lat, lon, state, name)
  }.toArray
  val satStations = stations.filter(s => s.state == "TX" && s.name.contains("SAN ANTONIO"))
  val satIDs = satStations.map(_.id).toSet
  satStations.foreach(println)
  println(satStations.size)
  val tempLines = scala.io.Source.fromFile("data/ghcn-daily/2017.csv").getLines
  val satMeasures = tempLines.map(_.split(",")).filter(d => satIDs.contains(d(0))).toArray
  println(satMeasures.length)
  println(satMeasures.map(_(2)).distinct.mkString(", "))
  println(satMeasures.filter(_(2) == "TMAX").length)
  println(satMeasures.filter(_(2) == "TMIN").length)
}