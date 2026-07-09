package com.sixclassguys.maplecalendar.domain.boss.dto

data class BossPartyCommonScheduleResponse(
    val selectedIndex: Int,       // 파티장이 최종 선택 시 다시 백엔드로 보낼 ID 역할
    val dayOfWeek: String,         // 예: "일요일", "월요일"
    val timeRange: String          // 예: "00:00 ~ 00:20"
)