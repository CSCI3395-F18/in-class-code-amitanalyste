package customml

import scala.util.Random

/**
 * Bernoulli distribution. Add parameters to the class as needed.
 */
class Bernoulli(rand: Random = Random) extends Distribution {
  def next(): Double = ???
  def mean(): Double = ???
  def variance(): Double = ???
}

object Bernoulli {
  def apply(data: Seq[Double]): Bernoulli = ???
}