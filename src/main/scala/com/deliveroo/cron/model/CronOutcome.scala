package com.deliveroo.cron.model

case class CronOutcome(minute: List[Int], hour: List[Int], dayOfMonth: List[Int], month: List[Int], dayOfWeek: List[Int], command: String) {

  def formatted: List[String] =
    List(
      "minute: " + minute.mkString(" "),
      "hour: " + hour.mkString(" "),
      "day of month: " + dayOfMonth.mkString(" "),
      "month: " + month.mkString(" "),
      "day of week: " + dayOfWeek.mkString(" "),
      "command: " + command
    )
}
