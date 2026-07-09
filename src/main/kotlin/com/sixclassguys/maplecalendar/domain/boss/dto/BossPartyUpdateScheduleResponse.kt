package com.sixclassguys.maplecalendar.domain.boss.dto

data class BossPartyUpdateScheduleResponse(
    val newAvailableSlots: String,
    val newKeepNextWeek: Boolean
)