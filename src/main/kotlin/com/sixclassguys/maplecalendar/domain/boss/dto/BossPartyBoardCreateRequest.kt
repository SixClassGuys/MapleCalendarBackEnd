package com.sixclassguys.maplecalendar.domain.boss.dto

import jakarta.validation.constraints.NotBlank

data class BossPartyBoardCreateRequest(
    @field:NotBlank(message = "게시글 내용")
    val content: String
)
