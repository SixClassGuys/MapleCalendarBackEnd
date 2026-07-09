package com.sixclassguys.maplecalendar.domain.boss.dto

data class BossAlarmCanceledEvent(
    val bossPartyId: Long,
    val triggerMemberName: String,
    val leaderEmail: String
)