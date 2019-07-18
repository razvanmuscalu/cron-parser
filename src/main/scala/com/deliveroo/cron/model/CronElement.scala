package com.deliveroo.cron.model
import scala.util.Try

sealed trait CronElement {
  val name: String
  val min: Int
  val max: Int
  val maxErrorMsg: String
  val minErrorMsg: String
}

case object Minute extends CronElement {
  val name: String = "minute"
  val min: Int = 0
  val max: Int = 59
  val maxErrorMsg: String = s"Maximum value for $name is $max"
  val minErrorMsg: String = s"Minimum value for $name is $max"
}

case object Hour extends CronElement {
  val name: String = "hour"
  val min: Int = 0
  val max: Int = 23
  val maxErrorMsg: String = s"Maximum value for $name is $max"
  val minErrorMsg: String = s"Minimum value for $name is $max"
}

case object Month extends CronElement {
  val name: String = "month"
  val min: Int = 1
  val max: Int = 12
  val maxErrorMsg: String = s"Maximum value for $name is $max"
  val minErrorMsg: String = s"Minimum value for $name is $min"
}

object CronElement {
  private val rangeErrorMsg = "Range should be specified from a lower number to a higher number"

  def fromString(value: String, element: CronElement): Either[String, List[Int]] = {
    val valueWithoutStep = value.split("/")(0)

    val interimList = valueWithoutStep match {
      case v if v.contains(",") => handleCommaSeparatedValues(valueWithoutStep, element)
      case v if v.contains("-") => handleRange(valueWithoutStep, element)
      case v =>
        Try(v.toInt).toEither match {
          case Right(i) => handleSingleValue(i, element)
          case Left(_)  => Left("Something wrong happened. Please check your input.")
        }
    }

    if (value.contains("/")) {
      val step = value.split("/")(1).toInt
      interimList.map(_.grouped(step).map(_.head).toList)
    } else interimList
  }

  private def handleCommaSeparatedValues(value: String, element: CronElement): Either[String, List[Int]] = {
    val list = value.split(",").map(_.toInt).toList
    if (list.exists(_ > element.max)) Left(element.maxErrorMsg) else Right(list)
  }

  private def handleRange(value: String, element: CronElement): Either[String, List[Int]] = {
    val range = value.split("-")
    val min = range(0).toInt
    val max = range(1).toInt
    if (min > max) Left(rangeErrorMsg)
    else if (min > element.max || max > element.max) Left(element.maxErrorMsg)
    else Right((min to max).toList)
  }

  private def handleSingleValue(i: Int, element: CronElement): Either[String, List[Int]] =
    if (i > element.max) Left(element.maxErrorMsg)
    else if (i < element.min) Left(element.minErrorMsg)
    else Right(List(i))
}
