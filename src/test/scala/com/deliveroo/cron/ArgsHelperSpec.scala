package com.deliveroo.cron

import com.deliveroo.cron.ArgsHelper.checkArgs
import com.deliveroo.cron.model.CronCommand
import org.scalatest.prop.{GeneratorDrivenPropertyChecks, TableDrivenPropertyChecks}

class ArgsHelperSpec extends BaseSpec with GeneratorDrivenPropertyChecks with TableDrivenPropertyChecks {

  private val argsExamples = Table(
    (
      "testCase",
      "args",
      "out"
    ),
    (
      "return None when 1 arg provided",
      Array(""),
      None
    ),
    (
      "return None when 5 args provided",
      Array("", "", "", "", ""),
      None
    ),
    (
      "return checked args",
      Array("*/15", "0", "1,15", "*", "1-5", "/usr/bin/find"),
      Some(CronCommand("*/15", "0", "1,15", "*", "1-5", "/usr/bin/find"))
    ),
    (
      "return checked args",
      Array("*/15", "0", "1,15", "*", "1-5", "/usr/bin/find", ""),
      Some(CronCommand("*/15", "0", "1,15", "*", "1-5", "/usr/bin/find"))
    )
  )

  "MainArgsHelper" should {
    "return checked args" in {
      forAll(argsExamples) { (_, args, out) =>
        checkArgs(args) shouldBe out
      }
    }
  }
}
