package com.deliveroo.cron.model

import com.deliveroo.cron.BaseSpec

class CronCommandSpec extends BaseSpec {

  private val cronCommand = CronCommand("*/15", "0", "1,15", "*", "1-5", "/usr/bin/find")

  "CronCommand" should {
    "parse minute" in {
      cronCommand.parsed.minute shouldBe List(1)
    }

    "parse hour" in {
      cronCommand.parsed.hour shouldBe List(2)
    }

    "parse day of month" in {
      cronCommand.parsed.dayOfMonth shouldBe List(3)
    }

    "parse month" in {
      cronCommand.parsed.month shouldBe List(4)
    }

    "parse day of week" in {
      cronCommand.parsed.dayOfWeek shouldBe List(5)
    }

    "return same command" in {
      cronCommand.parsed.command shouldBe cronCommand.command
    }

  }
}
