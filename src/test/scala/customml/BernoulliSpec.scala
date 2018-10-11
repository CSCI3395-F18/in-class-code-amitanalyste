package customml

import org.scalatest._

class BernoulliSpec extends FlatSpec with Matchers {
  "Bernoulli" should "have a mean of 0.5 with Array(0,1)" in {
    Bernoulli(Array(0,1)).mean() should be (0.5)
  }

  it should "have a variance of 0.25 with Array(0,1)" in {
    Bernoulli(Array(0,1)).variance() should be (0.25)
  }

  it should "have a mean of 0.25 with Array(0,1,0,0)" in {
    Bernoulli(Array(0,1,0,0)).mean() should be (0.25)
  }

  it should "have a variance of 3/16 with Array(0,1,0,0)" in {
    Bernoulli(Array(0,1,0,0)).variance() should be (3.0/16.0)
  }
}