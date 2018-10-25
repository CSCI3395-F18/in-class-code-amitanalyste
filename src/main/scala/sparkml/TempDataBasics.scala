package sparkml

import org.apache.spark.sql.types.IntegerType
import org.apache.spark.sql.types.DoubleType
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.types.StructField
import org.apache.spark.sql.types.StringType
import org.apache.spark.ml.feature.VectorAssembler

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

  val weatherDataAssembler = new VectorAssembler().
    setInputCols(Array("precip", "tmax", "tmin")).setOutputCol("weatherData")
  val weatherData = weatherDataAssembler.transform(data)
  weatherData.show()
  val highs = weatherData.select('weatherData)
  highs.show()
}