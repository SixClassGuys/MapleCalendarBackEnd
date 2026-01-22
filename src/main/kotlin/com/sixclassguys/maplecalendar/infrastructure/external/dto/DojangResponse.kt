package com.sixclassguys.maplecalendar.infrastructure.external.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class DojangResponse(
    @JsonProperty("ranking")
    val ranking: List<DojangRanking>
)
