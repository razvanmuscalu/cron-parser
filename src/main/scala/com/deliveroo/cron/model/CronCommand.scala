package com.deliveroo.cron.model

case class CronCommand(minute: String, hour: String, dayOfMonth: String, month: String, dayOfWeek: String, command: String)
