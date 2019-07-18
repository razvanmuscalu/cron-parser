package com.deliveroo.cron

import com.deliveroo.cron.ArgsHelper.checkArgs

object Main extends App {

  checkArgs(args) match {
    case Right(cronCommand) =>
      cronCommand.parsed match {
        case Right(cronOutcome) => cronOutcome.formatted.foreach(println)
        case Left(msg)          => println(msg)
      }
    case Left(msg) => println(msg)
  }
}
