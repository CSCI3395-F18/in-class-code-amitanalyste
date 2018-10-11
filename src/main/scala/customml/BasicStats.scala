package customml

/**
 * Use population formulas instead of sample formulas for all of the places where it applies.
 */
object BasicStats {
  def mean(x: Seq[Double]): Double = x.sum/x.length
  
  def variance(x: Seq[Double]): Double = ???
  
  def stdev(x: Seq[Double]): Double = ???
  
  def covariance(x: Seq[Double], y: Seq[Double]): Double = ???
  
  def correlation(x: Seq[Double], y: Seq[Double]): Double = ???
  
  def weightedMean(x: Seq[Double], weight: Double => Double): Double = ???
}