package customml

import org.scalatest._

class MultinomialSpec extends FlatSpec with Matchers {
  "Multinomial" should "have a mean of Seq(0.5,0.5) with inputs 1, Seq(Seq(1,0), Seq(0,1))" in {
    Multinomial(1, Seq(Seq(1,0), Seq(0,1))).mean() should be(Seq(0.5,0.5))
  }

  it should "have a variance of Seq(0.25,0.25) with inputs 1, Seq(Seq(1,0), Seq(0,1))" in {
    Multinomial(1, Seq(Seq(1,0), Seq(0,1))).variance() should be(Seq(0.25,0.25))
  }

}