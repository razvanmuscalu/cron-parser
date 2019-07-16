package com.deliveroo.cron.service

import com.deliveroo.cron.model.{CronCommand, CronOutcome}

trait CronService {
  def parse(cronCommand: CronCommand): CronOutcome
}

class CronServiceImpl extends CronService {

  def parse(cronCommand: CronCommand): CronOutcome = {
    val minute = List(1)
    val hour = List(2)
    val dayOfMonth = List(3)
    val month = List(4)
    val dayOfWeek = List(5)
    val command = cronCommand.command

    CronOutcome(minute, hour, dayOfMonth, month, dayOfWeek, command)
  }
}
