package com.deliveroo.cron

import com.deliveroo.cron.model.CronCommand

object ArgsHelper {

  def checkArgs(args: Array[String]): Either[String, CronCommand] =
    args.length match {
      case x if x < 6 => Left("You have to provide at least six arguments")
      case _ =>
        val minute = args(0)
        val hour = args(1)
        val dayOfMonth = args(2)
        val month = args(3)
        val dayOfWeek = args(4)
        val command = args(5)

        Right(CronCommand(minute, hour, dayOfMonth, month, dayOfWeek, command))
    }
}
