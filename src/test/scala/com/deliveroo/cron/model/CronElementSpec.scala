package com.deliveroo.cron.model
import com.deliveroo.cron.BaseSpec
import com.deliveroo.cron.model.CronElement.fromString
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
        ),
        (
          "handle step value over range",
          "2-6/2",
          Right(List(2, 4, 6))
        ),
        (
          "handle step value over comma separated list",
          "2,3,4,5,6/2",
          Right(List(2, 4, 6))
        ),
        (
          "handle step value (case 2)",
          "2-6/3",
          Right(List(2, 5))
        ),
        (
          "handle step value (case 3)",
          "2-6/10",
          Right(List(2))
        ),
        (
          "throw generic error if invalid arg (letter)",
          "a",
          Left("Something wrong happened. Please check your input.")
        ),
        (
          "throw generic error if invalid arg (letter for min in range)",
          "a-2",
          Left("Something wrong happened. Please check your input.")
        ),
        (
          "throw generic error if invalid arg (letter for max in range)",
          "2-a",
          Left("Something wrong happened. Please check your input.")
        ),
        (
          "throw generic error if invalid arg (first in comma separated values)",
          "a,2,3",
          Left("Something wrong happened. Please check your input.")
        ),
        (
          "throw generic error if invalid arg (last in comma separated values)",
          "2,a,3",
          Left("Something wrong happened. Please check your input.")
        ),
        (
          "throw generic error if invalid arg (mid in comma separated values)",
          "2,3,a",
          Left("Something wrong happened. Please check your input.")
        ),
        (
          "throw generic error if invalid arg in step over range",
          "2-3/a",
          Left("Something wrong happened. Please check your input.")
        ),
        (
          "throw generic error if invalid arg in step over comma separated values",
          "2,3/a",
          Left("Something wrong happened. Please check your input.")
        )
      )

      "for minute" in {
        forAll(argsExamples) { (_, arg, result) =>
          fromString(arg, Minute) shouldBe result
        }
      }

      "for hour" in {
        forAll(argsExamples) { (_, arg, result) =>
          fromString(arg, Hour) shouldBe result
        }
      }

      "for day of month" in {
        forAll(argsExamples) { (_, arg, result) =>
          fromString(arg, DayOfMonth) shouldBe result
        }
      }

      "for month" in {
        forAll(argsExamples) { (_, arg, result) =>
          fromString(arg, Month) shouldBe result
        }
      }

      "for day of week" in {
        forAll(argsExamples) { (_, arg, result) =>
          fromString(arg, DayOfWeek) shouldBe result
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
        ),
        (
          "handle wildcard",
          "*",
          Right((0 to 59).toList)
        ),
        (
          "handle wildcard with step value",
          "*/15",
          Right(List(0, 15, 30, 45))
        )
      )

      forAll(argsExamples) { (_, arg, result) =>
        fromString(arg, Minute) shouldBe result
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
        ),
        (
          "handle wildcard",
          "*",
          Right((0 to 23).toList)
        ),
        (
          "handle wildcard with step value",
          "*/6",
          Right(List(0, 6, 12, 18))
        )
      )

      forAll(argsExamples) { (_, arg, result) =>
        fromString(arg, Hour) shouldBe result
      }
    }

    "handle month specific use cases" in {
      val argsExamples = Table(
        (
          "testCase",
          "arg",
          "result"
        ),
        (
          "return error if arg higher than 12",
          "13",
          Left("Maximum value for month is 12")
        ),
        (
          "return error if arg smaller than 1",
          "0",
          Left("Minimum value for month is 1")
        ),
        (
          "return error if arg higher than 12 for max in range",
          "2-13",
          Left("Maximum value for month is 12")
        ),
        (
          "return error if arg higher than 60 for min in range",
          "13-14",
          Left("Maximum value for month is 12")
        ),
        (
          "return error if arg higher than 12 in comma separated values (first element)",
          "13,14",
          Left("Maximum value for month is 12")
        ),
        (
          "return error if arg higher than 12 in comma separated values (last element)",
          "11,12,13",
          Left("Maximum value for month is 12")
        ),
        (
          "return error if arg higher than 12 in comma separated values (mid element)",
          "12,13,14",
          Left("Maximum value for month is 12")
        ),
        (
          "handle wildcard",
          "*",
          Right((1 to 12).toList)
        ),
        (
          "handle wildcard with step value",
          "*/3",
          Right(List(1, 4, 7, 10))
        )
      )

      forAll(argsExamples) { (_, arg, result) =>
        fromString(arg, Month) shouldBe result
      }
    }

    "handle day of month specific use cases" in {
      val argsExamples = Table(
        (
          "testCase",
          "arg",
          "result"
        ),
        (
          "return error if arg higher than 31",
          "32",
          Left("Maximum value for day of month is 31")
        ),
        (
          "return error if arg smaller than 1",
          "0",
          Left("Minimum value for day of month is 1")
        ),
        (
          "return error if arg higher than 31 for max in range",
          "21-32",
          Left("Maximum value for day of month is 31")
        ),
        (
          "return error if arg higher than 31 for min in range",
          "32-34",
          Left("Maximum value for day of month is 31")
        ),
        (
          "return error if arg higher than 31 in comma separated values (first element)",
          "32,33",
          Left("Maximum value for day of month is 31")
        ),
        (
          "return error if arg higher than 31 in comma separated values (last element)",
          "30,31,32",
          Left("Maximum value for day of month is 31")
        ),
        (
          "return error if arg higher than 31 in comma separated values (mid element)",
          "31,32,33",
          Left("Maximum value for day of month is 31")
        ),
        (
          "handle wildcard",
          "*",
          Right((1 to 31).toList)
        ),
        (
          "handle wildcard with step value",
          "*/10",
          Right(List(1, 11, 21, 31))
        )
      )

      forAll(argsExamples) { (_, arg, result) =>
        fromString(arg, DayOfMonth) shouldBe result
      }
    }

    "handle day of week specific use cases" in {
      val argsExamples = Table(
        (
          "testCase",
          "arg",
          "result"
        ),
        (
          "return error if arg higher than 7",
          "8",
          Left("Maximum value for day of week is 7")
        ),
        (
          "return error if arg smaller than 1",
          "0",
          Left("Minimum value for day of week is 1")
        ),
        (
          "return error if arg higher than 7 for max in range",
          "6-8",
          Left("Maximum value for day of week is 7")
        ),
        (
          "return error if arg higher than 7 for min in range",
          "8-9",
          Left("Maximum value for day of week is 7")
        ),
        (
          "return error if arg higher than 7 in comma separated values (first element)",
          "8,9",
          Left("Maximum value for day of week is 7")
        ),
        (
          "return error if arg higher than 7 in comma separated values (last element)",
          "6,7,8",
          Left("Maximum value for day of week is 7")
        ),
        (
          "return error if arg higher than 7 in comma separated values (mid element)",
          "7,8,9",
          Left("Maximum value for day of week is 7")
        ),
        (
          "handle wildcard",
          "*",
          Right((1 to 7).toList)
        ),
        (
          "handle wildcard with step value",
          "*/2",
          Right(List(1, 3, 5, 7))
        )
      )

      forAll(argsExamples) { (_, arg, result) =>
        fromString(arg, DayOfWeek) shouldBe result
      }
    }
  }
}
