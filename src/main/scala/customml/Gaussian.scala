package customml

class Gaussian { // extends Distribution {
  def next(): Double = ???
  def mean(): Double = ???
  def variance(): Double = ???
}

object Gaussian {
  def apply(data: Seq[Double]): Gaussian = ???
}