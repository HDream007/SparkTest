package com.rimi.spark.day01;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;

/**
 * word count
 */
public class WC {
    public static void main(String[] args) {

        SparkConf sparkConf = new SparkConf().setAppName("WC").setMaster("Local[4]");

        JavaSparkContext jsc = new JavaSparkContext(sparkConf);

        JavaRDD<String> textFile = jsc.textFile("");

        JavaRDD<String> flatMap = textFile.flatMap((FlatMapFunction<String, String>) s -> Arrays.asList(s.split(" ")).iterator());

        JavaPairRDD<String, Integer> map = flatMap.mapToPair((PairFunction<String, String, Integer>) word -> new Tuple2<>(word, 1));

        //聚合
        JavaPairRDD<String, Integer> reduceByKey = map.reduceByKey((Function2<Integer, Integer, Integer>) (v1, v2) -> v1 + v2);

        //遍历输出
        reduceByKey.foreach((VoidFunction<Tuple2<String, Integer>>) System.out::println);

    }
}
