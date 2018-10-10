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
    csv("data/SanAntonioTemps.csv")
    
  data.show()
  data.describe().show()
  
  data.select(('day+('month-1)*31 === 'dayOfYear), sqrt('day)).show()
  data.agg(count('day)).show()
  println(data.stat.corr("precip", "tmax"))
  
  data.createOrReplaceTempView("sadata")
  val rainy = spark.sql("""SELECT * FROM sadata WHERE precip>0.1""")
  rainy.show()
  
}