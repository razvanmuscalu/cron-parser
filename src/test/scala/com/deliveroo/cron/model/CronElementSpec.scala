package com.deliveroo.cron.model
import com.deliveroo.cron.BaseSpec
import com.deliveroo.cron.model.CronElement.{Hour, Minute}
import org.scalatest.prop.TableDrivenPropertyChecks

class CronElementSpec extends BaseSpec with TableDrivenPropertyChecks {

  "CronElement" should {
    "handle common use cases" should {
      val argsExamples = Table(
        (
          "testCase",
          "arg",
          "result"
        ),
        (
          "return 1",
          "1",
          Right(List(1))
        ),
        (
          "return 2",
          "2",
          Right(List(2))
        ),
        (
          "handle comma separated values",
          "1,2",
          Right(List(1, 2))
        ),
        (
          "handle range",
          "3-5",
          Right(List(3, 4, 5))
        ),
        (
          "do not allow if range is not specified in ascending order",
          "5-3",
          Left("Range should be specified from a lower number to a higher number")
        )
      )

      "for minute" in {
        forAll(argsExamples) { (_, arg, result) =>
          Minute.fromString(arg) shouldBe result
        }
      }

      "for hour" in {
        forAll(argsExamples) { (_, arg, result) =>
          Hour.fromString(arg) shouldBe result
        }
      }
    }

    "handle minute specific use cases" in {
      val argsExamples = Table(
        (
          "testCase",
          "arg",
          "result"
        ),
        (
          "return error if arg higher than 60",
          "60",
          Left("Maximum value for minute is 59")
        ),
        (
          "return error if arg higher than 60 for max in range",
          "40-60",
          Left("Maximum value for minute is 59")
        ),
        (
          "return error if arg higher than 60 for min in range",
          "60-62",
          Left("Maximum value for minute is 59")
        ),
        (
          "return error if arg higher than 60 in comma separated values (first element)",
          "60,61",
          Left("Maximum value for minute is 59")
        ),
        (
          "return error if arg higher than 60 in comma separated values (last element)",
          "58,59,60",
          Left("Maximum value for minute is 59")
        ),
        (
          "return error if arg higher than 60 in comma separated values (mid element)",
          "59,60,61",
          Left("Maximum value for minute is 59")
        )
      )

      forAll(argsExamples) { (_, arg, result) =>
        Minute.fromString(arg) shouldBe result
      }
    }

    "handle hour specific use cases" in {
      val argsExamples = Table(
        (
          "testCase",
          "arg",
          "result"
        ),
        (
          "return error if arg higher than 24",
          "24",
          Left("Maximum value for hour is 23")
        ),
        (
          "return error if arg higher than 24 for max in range",
          "23-25",
          Left("Maximum value for hour is 23")
        ),
        (
          "return error if arg higher than 24 for min in range",
          "25-27",
          Left("Maximum value for hour is 23")
        ),
        (
          "return error if arg higher than 24 in comma separated values (first element)",
          "24,25",
          Left("Maximum value for hour is 23")
        ),
        (
          "return error if arg higher than 24 in comma separated values (last element)",
          "22,23,24",
          Left("Maximum value for hour is 23")
        ),
        (
          "return error if arg higher than 24 in comma separated values (mid element)",
          "23,24,25",
          Left("Maximum value for hour is 23")
        )
      )

      forAll(argsExamples) { (_, arg, result) =>
        Hour.fromString(arg) shouldBe result
      }
    }
  }
}
