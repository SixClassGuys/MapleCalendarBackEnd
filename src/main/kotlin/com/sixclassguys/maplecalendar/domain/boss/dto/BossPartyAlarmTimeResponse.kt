package com.sixclassguys.maplecalendar.domain.boss.dto

import java.time.LocalDateTime

data class BossPartyAlarmTimeResponse(
    val id: Long,
    val alarmTime: LocalDateTime,
    val message: String,
    val isSent: Boolean
)
