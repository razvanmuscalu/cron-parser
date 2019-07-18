package com.deliveroo.cron.model
import scala.util.Try

sealed trait CronElement {
  val name: String
  val min: Int
  val max: Int
  def maxErrorMsg: String = s"Maximum value for $name is $max"
  def minErrorMsg: String = s"Minimum value for $name is $min"
}

case object Minute extends CronElement {
  val name: String = "minute"
  val min: Int = 0
  val max: Int = 59
}

case object Hour extends CronElement {
  val name: String = "hour"
  val min: Int = 0
  val max: Int = 23
}

case object DayOfMonth extends CronElement {
  val name: String = "day of month"
  val min: Int = 1
  val max: Int = 31
}

case object Month extends CronElement {
  val name: String = "month"
  val min: Int = 1
  val max: Int = 12
}

case object DayOfWeek extends CronElement {
  val name: String = "day of week"
  val min: Int = 1
  val max: Int = 7
}

object CronElement {
  def fromString(value: String, element: CronElement): Either[String, List[Int]] = {
    val valueWithoutStep = value.split("/")(0)

    val interimList = valueWithoutStep match {
      case v if v.startsWith("*") => Right((element.min to element.max).toList)
      case v if v.contains(",")   => handleCommaSeparatedValues(valueWithoutStep, element)
      case v if v.contains("-")   => handleRange(valueWithoutStep, element)
      case v                      => handleSingleValue(v, element)
    }

    if (value.contains("/")) handleStep(value, interimList) else interimList
  }

  private def handleCommaSeparatedValues(value: String, element: CronElement): Either[String, List[Int]] = {
    val list = value
      .split(",")
      .map(toInt)
      .toList
      .foldRight(Right(Nil): Either[String, List[Int]]) { (elem, acc) =>
        acc.right.flatMap(list => elem.right.map(_ :: list))
      }

    list.flatMap { l =>
      if (l.exists(_ > element.max)) Left(element.maxErrorMsg) else Right(l)
    }

  }

  private def handleRange(value: String, element: CronElement): Either[String, List[Int]] = {
    val range = value.split("-")

    for {
      min <- toInt(range(0))
      max <- toInt(range(1))
      res <- if (min > max) Left("Range should be specified from a lower number to a higher number")
      else if (min > element.max || max > element.max) Left(element.maxErrorMsg)
      else Right((min to max).toList)
    } yield res
  }

  private def handleSingleValue(value: String, element: CronElement): Either[String, List[Int]] =
    toInt(value).flatMap { i =>
      if (i > element.max) Left(element.maxErrorMsg)
      else if (i < element.min) Left(element.minErrorMsg)
      else Right(List(i))
    }

  private def handleStep(value: String, interimList: Either[String, List[Int]]): Either[String, List[Int]] = {
    val step = value.split("/")(1)
    toInt(step).flatMap { i =>
      interimList.map(_.grouped(i).map(_.head).toList)
    }
  }

  private def toInt(v: String): Either[String, Int] =
    Try(v.toInt).toEither match {
      case Right(i) => Right(i)
      case Left(_)  => Left("Something wrong happened. Please check your input.")
    }
}
