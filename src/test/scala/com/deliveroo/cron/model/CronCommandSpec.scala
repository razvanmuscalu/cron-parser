package com.deliveroo.cron.model

import com.deliveroo.cron.BaseSpec

class CronCommandSpec extends BaseSpec {

  private val cronCommand = CronCommand("1", "2", "3", "4", "5", "/usr/bin/find")

  "CronCommand" should {
    "parse minute" in {
      cronCommand.parsed.map(_.minute) shouldBe Right(List(1))
    }

    "return thrown error for minute" in {
      val cronCommand = CronCommand("60", "2", "3", "4", "5", "/usr/bin/find")
      cronCommand.parsed shouldBe Left("Maximum value for minute is 59")
    }

    "parse hour" in {
      cronCommand.parsed.map(_.hour) shouldBe Right(List(2))
    }

    "return thrown error for hour" in {
      val cronCommand = CronCommand("1", "24", "3", "4", "5", "/usr/bin/find")
      cronCommand.parsed shouldBe Left("Maximum value for hour is 23")
    }

    "parse day of month" in {
      cronCommand.parsed.map(_.dayOfMonth) shouldBe Right(List(3))
    }

    "parse month" in {
      cronCommand.parsed.map(_.month) shouldBe Right(List(4))
    }

    "return thrown error for month" in {
      val cronCommand = CronCommand("1", "2", "3", "13", "5", "/usr/bin/find")
      cronCommand.parsed shouldBe Left("Maximum value for month is 12")
    }

    "parse day of week" in {
      cronCommand.parsed.map(_.dayOfWeek) shouldBe Right(List(5))
    }

    "return same command" in {
      cronCommand.parsed.map(_.command) shouldBe Right(cronCommand.command)
    }

  }
}
