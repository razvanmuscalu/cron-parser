package com.deliveroo.cron.service

import com.deliveroo.cron.BaseSpec
import com.deliveroo.cron.model.CronCommand

class CronServiceSpec extends BaseSpec {

  private val cronCommand = CronCommand("*/15", "0", "1,15", "*", "1-5", "/usr/bin/find")

  private val cronService = new CronServiceImpl

  "CronService" should {
    "parse minute" in {
      val resp = cronService.parse(cronCommand)

      resp.minute shouldBe List(1)
    }

    "parse hour" in {
      val resp = cronService.parse(cronCommand)

      resp.hour shouldBe List(2)
    }

    "parse day of month" in {
      val resp = cronService.parse(cronCommand)

      resp.dayOfMonth shouldBe List(3)
    }

    "parse month" in {
      val resp = cronService.parse(cronCommand)

      resp.month shouldBe List(4)
    }

    "parse day of week" in {
      val resp = cronService.parse(cronCommand)

      resp.dayOfWeek shouldBe List(5)
    }

    "return same command" in {
      val resp = cronService.parse(cronCommand)

      resp.command shouldBe cronCommand.command
    }

  }
}
