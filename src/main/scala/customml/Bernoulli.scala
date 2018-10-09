package customml

import scala.util.Random

/**
 * Bernoulli distribution. Add parameters to the class as needed.
 */
class Bernoulli(p: Double, rand: Random = Random) extends Distribution {
  def next(): Double = if(rand.nextDouble() < p) 1.0 else 0.0
  def mean(): Double = ???
  def variance(): Double = ???
}

object Bernoulli {
  def apply(data: Seq[Double]): Bernoulli = ???
}