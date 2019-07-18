package com.deliveroo.cron.model
import com.deliveroo.cron.model.CronElement.fromString

case class CronCommand(minute: String, hour: String, dayOfMonth: String, month: String, dayOfWeek: String, command: String) {

  def parsed: Either[String, CronOutcome] =
    for {
      parsedMinute <- fromString(minute, Minute)
      parsedHour <- fromString(hour, Hour)
      parsedDayOfMonth <- fromString(dayOfMonth, DayOfMonth)
      parsedMonth <- fromString(month, Month)
      parsedDayOfWeek <- fromString(dayOfWeek, DayOfWeek)
    } yield CronOutcome(parsedMinute, parsedHour, parsedDayOfMonth, parsedMonth, parsedDayOfWeek, command)
}
