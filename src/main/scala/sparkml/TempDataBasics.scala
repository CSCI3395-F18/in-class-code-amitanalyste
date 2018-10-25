package sparkml

import org.apache.spark.sql.types.IntegerType
import org.apache.spark.sql.types.DoubleType
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.types.StructField
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.functions._
import org.apache.spark.ml.linalg
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.linalg.SQLDataTypes.VectorType
import org.apache.spark.ml.feature.StandardScaler
import org.apache.spark.ml.regression.LinearRegression

object TempDataBasics extends App {
  val spark = SparkSession.builder().master("local[*]").appName("Temp Data").getOrCreate()
  import spark.implicits._

  spark.sparkContext.setLogLevel("WARN")

  val schema = StructType(Array(
    StructField("day", IntegerType),
    StructField("dayOfYear", IntegerType),
    StructField("month", IntegerType),
    StructField("state", StringType),
    StructField("year", IntegerType),
    StructField("precip", DoubleType),
    StructField("tave", DoubleType),
    StructField("tmax", DoubleType),
    StructField("tmin", DoubleType)))

  val data = spark.read.schema(schema).
    //option("inferSchema", "true").
    option("header", "true").
    // option("dateFormat", "yyyyMMdd")
    csv("data/SanAntonioTemps.csv")

  data.show()

  // First transform stuff
  val weatherDataAssembler = new VectorAssembler().
    setInputCols(Array("precip", "tmax", "tmin")).setOutputCol("weatherData")
  val weatherData = weatherDataAssembler.transform(data)
  weatherData.show()
  weatherData.describe().show()
  val highs = weatherData.map(_.getAs[linalg.Vector]("weatherData")(1))
  highs.show()
  val scaler = new StandardScaler().setInputCol("weatherData").
    setOutputCol("scaledWeatherData")
  val scalerModel = scaler.fit(weatherData)
  println(scalerModel.mean, scalerModel.std)
  val scaledWeatherData = scalerModel.transform(weatherData)
  scaledWeatherData.show(false)
  
  // Find linear change of temp over time.
  val maxVA = new VectorAssembler().setInputCols(Array("time")).setOutputCol("timeVect")
  val maxTempDF = data.withColumn("time", 'year+'dayOfYear/365.0)
  val maxWithVector = maxVA.transform(maxTempDF)
  val Array(train, test) = maxWithVector.randomSplit(Array(0.8, 0.2))
  val maxLR = new LinearRegression().setMaxIter(10).setFeaturesCol("timeVect")
    .setLabelCol("tmax")
  val maxLRModel = maxLR.fit(train)
  println(maxLRModel.coefficients, maxLRModel.intercept)
  println(maxLRModel.summary.rootMeanSquaredError)
  val fittedData = maxLRModel.transform(test)
  
  // Find sinusoidal fit over time, better model.
  val sinVA = new VectorAssembler().setInputCols(Array("time", "sine", "cosine")).setOutputCol("features")
  val sinTempDF = data.withColumn("time", 'year+'dayOfYear/365.0)
    .withColumn("sine", sin('time*2*math.Pi))
    .withColumn("cosine", cos('time*2*math.Pi))
  val sinWithVector = sinVA.transform(sinTempDF)
  val Array(sinTrain, sinTest) = sinWithVector.randomSplit(Array(0.8, 0.2))
  val sinLR = new LinearRegression().setMaxIter(10)
    .setLabelCol("tmax")
  val sinLRModel = sinLR.fit(sinTrain)
  println(sinLRModel.coefficients, sinLRModel.intercept)
  println(sinLRModel.summary.rootMeanSquaredError)
}




