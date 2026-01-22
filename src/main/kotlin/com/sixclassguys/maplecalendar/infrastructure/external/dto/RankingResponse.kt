package com.sixclassguys.maplecalendar.infrastructure.external.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class RankingResponse(
    @JsonProperty("ranking")
    val ranking: List<Ranking>
)
