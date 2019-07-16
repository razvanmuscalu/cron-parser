package com.deliveroo.cron

import com.deliveroo.cron.ArgsHelper.checkArgs
import com.deliveroo.cron.service.{CronService, CronServiceImpl}

object Main extends App {

  private val cronService: CronService = new CronServiceImpl

  checkArgs(args) match {
    case Right(cronCommand) => cronService.parse(cronCommand).formatted.foreach(println)
    case Left(msg)          => println(msg)
  }
}
