package com.sixclassguys.maplecalendar.domain.boss.dto

data class BossPartyScheduleWebsocketResponse(
    val type: String = "SCHEDULE_UPDATE",
    val candidates: List<BossPartyCommonScheduleResponse>
)