package customml

class Multinomial extends DistributionK {
  def next(): Seq[Double] = ???
  def mean(): Seq[Double] = ???
  def variance(): Seq[Double] = ???
}

object Multinomial {
  def apply(n: Int, data: Seq[Seq[Double]]): Multinomial = ???
}