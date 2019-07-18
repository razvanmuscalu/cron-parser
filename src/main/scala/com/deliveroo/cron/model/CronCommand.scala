package com.deliveroo.cron.model
import com.deliveroo.cron.model.CronElement.fromString

case class CronCommand(minute: String, hour: String, dayOfMonth: String, month: String, dayOfWeek: String, command: String) {

  def parsed: Either[String, CronOutcome] =
    for {
      parsedMinute <- fromString(minute, Minute)
      parsedHour <- fromString(hour, Hour)
      dayOfMonth = List(3)
      parsedMonth <- fromString(month, Month)
      dayOfWeek = List(5)
    } yield CronOutcome(parsedMinute, parsedHour, dayOfMonth, parsedMonth, dayOfWeek, command)
}
