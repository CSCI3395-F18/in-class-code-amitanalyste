package customml

class Binomial extends Distribution {
  def next(): Double = ???
  def mean(): Double = ???
  def variance(): Double = ???
}

object Binomial {
  def apply(n: Int, data: Seq[Double]): Binomial = ???
}