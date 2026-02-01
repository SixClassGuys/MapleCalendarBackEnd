package com.sixclassguys.maplecalendar.domain.boss.dto

import com.sixclassguys.maplecalendar.domain.boss.enums.BossPartyChatMessageType
import java.time.LocalDateTime

data class BossPartyChatMessageResponse(
    val id: Long,
    val characterId: Long,
    val characterName: String,
    val content: String,
    val messageType: BossPartyChatMessageType,
    val createdAt: LocalDateTime
)
