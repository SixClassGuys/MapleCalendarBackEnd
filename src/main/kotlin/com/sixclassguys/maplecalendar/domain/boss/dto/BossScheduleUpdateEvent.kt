package com.sixclassguys.maplecalendar.domain.boss.dto

data class BossScheduleUpdateEvent(
    val partyId: Long,
    val candidates: List<BossPartyCommonScheduleResponse>,
    val isAlarmCanceled: Boolean = false,
    val triggerMemberName: String? = null,
    val canceledDayOfWeek: String? = null,
    val canceledTimeRange: String? = null
)