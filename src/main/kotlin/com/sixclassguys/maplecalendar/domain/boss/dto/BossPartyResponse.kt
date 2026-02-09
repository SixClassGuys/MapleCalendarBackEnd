package com.sixclassguys.maplecalendar.domain.boss.dto

import com.sixclassguys.maplecalendar.domain.boss.enums.BossDifficulty
import com.sixclassguys.maplecalendar.domain.boss.enums.BossType
import java.time.LocalDateTime

data class BossPartyResponse(
    val id: Long,
    val title: String,
    val description: String,
    val boss: BossType,
    val difficulty: BossDifficulty,
    val isPartyAlarmEnabled: Boolean,
    val isChatAlarmEnabled: Boolean,
    val leaderNickname: String,
    val memberCount: Int,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
)