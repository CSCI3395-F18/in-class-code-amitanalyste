package customml

import org.scalatest._

class GaussianSpec extends FlatSpec with Matchers {
  "Gaussian" should "have mean of 0 for input Seq(-1, 1)" in {
    Gaussian(Seq(-1, 1)).mean() should be  (0.0)
  }

  it should "have variance of 1 for input Seq(-1, 1)" in {
    Gaussian(Seq(-1, 1)).variance() should be  (1.0)
  }
}