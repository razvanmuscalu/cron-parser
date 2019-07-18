package com.deliveroo.cron.model
import com.deliveroo.cron.model.CronElement.{Hour, Minute}

case class CronCommand(minute: String, hour: String, dayOfMonth: String, month: String, dayOfWeek: String, command: String) {

  def parsed: Either[String, CronOutcome] = {
    for {
      parsedMinute <- Minute.fromString(minute)
      parsedHour <- Hour.fromString(hour)
      dayOfMonth = List(3)
      month = List(4)
      dayOfWeek = List(5)
    } yield CronOutcome(parsedMinute, parsedHour, dayOfMonth, month, dayOfWeek, command)
  }
}
