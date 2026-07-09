package com.sixclassguys.maplecalendar.domain.boss.dto

data class BossPartyUpdateScheduleRequest(
    val availableSlots: String,
    val keepNextWeek: Boolean
)