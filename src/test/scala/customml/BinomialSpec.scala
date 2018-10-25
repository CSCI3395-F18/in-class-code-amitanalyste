package customml

import org.scalatest._

class BinomialSpec extends FlatSpec with Matchers {
  "Binomial" should "have a mean of 0.5 with inputs 1, Seq(1,0)" in {
    Binomial(1, Seq(1,0)).mean() should be(0.5)
  }

  it should "have a variance of 0.25 with inputs 1, Seq(1,0)" in {
    Binomial(1, Seq(1,0)).variance() should be(0.25)
  }
  
}