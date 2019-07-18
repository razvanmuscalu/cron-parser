package com.deliveroo.cron.model

sealed trait CronElement {
  def fromString(value: String): Either[String, List[Int]]
}

object CronElement {
  final case object Minute extends CronElement {
    def fromString(value: String): Either[String, List[Int]] =
      if (value.contains(",")) {
        val list = value.split(",").map(_.toInt).toList
        if (list.exists(_ > 59)) Left("Maximum value for minute is 59") else Right(list)
      } else if (value.contains("-")) {
        val range = value.split("-")
        val min = range(0).toInt
        val max = range(1).toInt
        if (min > max) Left("Range should be specified from a lower number to a higher number")
        else if (min > 59 || max > 59) Left("Maximum value for minute is 59")
        else Right((min to max).toList)
      } else {
        if (value.toInt > 59) Left("Maximum value for minute is 59") else Right(List(value.toInt))
      }
  }

  final case object Hour extends CronElement {
    def fromString(value: String): Either[String, List[Int]] =
      if (value.contains(",")) {
        val list = value.split(",").map(_.toInt).toList
        if (list.exists(_ > 23)) Left("Maximum value for hour is 23") else Right(list)
      } else if (value.contains("-")) {
        val range = value.split("-")
        val min = range(0).toInt
        val max = range(1).toInt
        if (min > max) Left("Range should be specified from a lower number to a higher number")
        else if (min > 23 || max > 23) Left("Maximum value for hour is 23")
        else Right((min to max.toInt).toList)
      } else {
        if (value.toInt > 23) Left("Maximum value for hour is 23") else Right(List(value.toInt))
      }
  }
}
