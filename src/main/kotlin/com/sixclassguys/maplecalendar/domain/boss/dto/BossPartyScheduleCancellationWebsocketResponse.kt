package com.sixclassguys.maplecalendar.domain.boss.dto

data class BossPartyScheduleCancellationWebsocketResponse(
    val triggerMemberName: String,
    val canceledDayOfWeek: String?,
    val canceledTimeRange: String?
)