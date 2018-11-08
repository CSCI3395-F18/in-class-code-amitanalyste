package sparkml

import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.feature.StringIndexer
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.classification.RandomForestClassifier
import org.apache.spark.sql.functions._
import org.apache.spark.ml.evaluation.BinaryClassificationEvaluator

object IncomeClassify {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("Income Classification").master("local[*]").getOrCreate()
    import spark.implicits._
    
    spark.sparkContext.setLogLevel("WARN")
    
    val data = spark.read.option("header", true).
      option("inferSchema",true).csv("data/adult.csv").cache()
    val stringFeatureCols = "workclass maritalStatus occupation relationship race sex".split(" ")
    val intFeatureCols = "age educationNum capitalGain capitalLoss hoursPerWeek".split(" ")
    val indexedData = stringFeatureCols.foldLeft(data) { (ds, name) =>
      val indexer = new StringIndexer().setInputCol(name).setOutputCol(name+"-i")
      indexer.fit(ds).transform(ds)
    }.withColumn("label", when('income === ">50K", 1).otherwise(0))
    val assembler = new VectorAssembler().
      setInputCols(intFeatureCols ++ stringFeatureCols.map(_+"-i")).
      setOutputCol("features")
    val assembledData = assembler.transform(indexedData)
    
    val Array(train, test) = assembledData.randomSplit(Array(0.8, 0.2)).map(_.cache())
    data.unpersist()
    val rf = new RandomForestClassifier()
    val model = rf.fit(train)
    
    val predictions = model.transform(test)
    predictions.show()
    val evaluator = new BinaryClassificationEvaluator
    val accuracy = evaluator.evaluate(predictions)
    println("accuracy="+accuracy)
    
    spark.close()
  }
}