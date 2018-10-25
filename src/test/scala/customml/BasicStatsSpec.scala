package customml

import org.scalatest._

class TestBasicStats extends FlatSpec with Matchers {
  "mean" should "return 4 when called on Array(3,4,5)" in {
    BasicStats.mean(Array(3,4,5)) should be (4.0)
  }
  
  it should "return 5 when called on Array(1,2,8,9)" in {
    BasicStats.mean(Array(1,2,8,9)) should be (5.0)
  }

  "variance" should "return 1 when called on Array(1,3)" in {
    BasicStats.variance(Array(1,3)) should be (1.0)
  }
  
  it should "return 6 when called on Array(1, 4, 7)" in {
    BasicStats.variance(Array(1,4,7)) should be (6.0)
  }
  
  "stdev" should "return 1 when called on Array(1,3)" in {
    BasicStats.stdev(Array(1,3)) should be (1.0)
  }
  
  it should "return sqrt(10) in Array(1, 2, 5, 8, 9)" in {
    BasicStats.stdev(Array(1,2,5,8,9)) should be (math.sqrt(10))
  }
  
  "covariance" should "return 1 when called on Array(1,3) and Array(4,6)" in {
    BasicStats.covariance(Array(1,3), Array(4,6)) should be (1.0)
  }
  
  it should "return -1 when called on Array(1,3) and Array(4,2)" in {
    BasicStats.covariance(Array(1,3), Array(4,2)) should be (-1.0)
  }

  it should "return 0 when called on Array(1,3) and Array(4,4)" in {
    BasicStats.covariance(Array(1,3), Array(4,4)) should be (0.0)
  }

  it should "return 4/3 when called on Array(1,3,5) and Array(4,5,6)" in {
    BasicStats.covariance(Array(1,3,5), Array(4,5,6)) should be (4.0/3.0)
  }

  "correlation" should "return 1 when called on Array(1,3) and Array(4,6)" in {
    BasicStats.correlation(Array(1,3), Array(4,6)) should be (1.0)
  }
  
  it should "return -1 when called on Array(1,3) and Array(4,2)" in {
    BasicStats.correlation(Array(1,3), Array(4,2)) should be (-1.0)
  }

  it should "return 1 when called on Array(1,3,5) and Array(4,5,6)" in {
    BasicStats.correlation(Array(1,3,5), Array(4,5,6)) should be (1.0)
  }

  it should "return 4*sqrt(2/35) when called on Array(1,3,5,2) and Array(4,5,6,5)" in {
    BasicStats.correlation(Array(1,3,5,2), Array(4,5,6,5)) should be (4.0*math.sqrt(2.0/35.0))
  }

  "weightedMean" should "return 1.6 when called on Array(1,4) with weight function of 1/x" in {
    BasicStats.weightedMean(Array(1,4), x => 1/x) should be (1.6)
  }
}
