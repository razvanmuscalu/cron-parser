package com.deliveroo.cron.model
import com.deliveroo.cron.BaseSpec

class CronOutcomeSpec extends BaseSpec {

  "CronOutcome" should {
    "format" in {
      val output = CronOutcome(List(1, 2, 3), List(2, 3, 4), List(3, 4, 5), List(4, 5, 6), List(5, 6, 7), "/usr/bin/find").formatted
      output.head shouldBe "minute: 1 2 3"
      output(1) shouldBe "hour: 2 3 4"
      output(2) shouldBe "day of month: 3 4 5"
      output(3) shouldBe "month: 4 5 6"
      output(4) shouldBe "day of week: 5 6 7"
      output.last shouldBe "command: /usr/bin/find"
    }
  }
}
