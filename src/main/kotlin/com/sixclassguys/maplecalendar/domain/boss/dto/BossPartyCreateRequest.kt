package com.sixclassguys.maplecalendar.domain.boss.dto

import com.sixclassguys.maplecalendar.domain.boss.enums.BossDifficulty
import com.sixclassguys.maplecalendar.domain.boss.enums.BossType
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class BossPartyCreateRequest(
    @field:NotNull val boss: BossType,
    @field:NotNull val difficulty: BossDifficulty,
    @field:NotBlank val title: String,
    @field:NotBlank val description: String,
    @field:NotNull val characterId: Long,
)
