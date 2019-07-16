package com.deliveroo.cron.model

case class CronCommand(minute: String, hour: String, dayOfMonth: String, month: String, dayOfWeek: String, command: String) {

  def parsed: CronOutcome = {
    val minute = List(1)
    val hour = List(2)
    val dayOfMonth = List(3)
    val month = List(4)
    val dayOfWeek = List(5)

    CronOutcome(minute, hour, dayOfMonth, month, dayOfWeek, command)
  }
}
