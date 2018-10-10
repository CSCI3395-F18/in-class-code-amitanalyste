package customml

import org.scalatest._

class TestBasicStats extends FlatSpec with Matchers {
  "mean" should "return 4 when called on Array(3,4,5)" in {
    BasicStats.mean(Array(3,4,5)) should be (4.0)
  }
  
//  it should "return 9 when called on 3" in {
//    HelloWorld.square(3) should be (9.0)
//  }
//  
//  "cube" should "return 8 when called on 2" in {
//    HelloWorld.cube(2) should be (8)
//  }
}
