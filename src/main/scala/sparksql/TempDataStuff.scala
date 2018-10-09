package sparksql

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.Column
import org.apache.spark.sql._

object TempDataStuff extends App {
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
      StructField("tmin", DoubleType)
      ))
  
  val data = spark.read.schema(schema).
    //option("inferSchema", "true").
    option("header","true").
    // option("dateFormat", "yyyyMMdd")
    csv("/users/mlewis/workspaceF18/CSCI3395-F18/data/SanAntonioTemps.csv")
    
  data.show()
  data.describe().show()
  
  data.select((data("day")+(data("month")-1)*31 === data("dayOfYear")), sqrt(col("day"))).show()
  data.agg(count(data("day"))).show()
  println(data.stat.corr("precip", "tmax"))
  
  data.createOrReplaceTempView("data")
  data.select("""
    SELECT * FROM data WHERE precip>0.1
  """)
  
}