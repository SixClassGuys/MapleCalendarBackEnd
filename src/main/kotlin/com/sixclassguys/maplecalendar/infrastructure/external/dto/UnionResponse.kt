package com.sixclassguys.maplecalendar.infrastructure.external.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.OffsetDateTime

data class UnionResponse(
    @JsonProperty("date")
    val date: OffsetDateTime?,

    @JsonProperty("union_level")
    val unionLevel: Int,

    @JsonProperty("union_grade")
    val unionGrade: String,

    @JsonProperty("union_artifact_level")
    val unionArtifactLevel: Int,

    @JsonProperty("union_artifact_exp")
    val unionArtifactExp: Long,

    @JsonProperty("union_artifact_point")
    val unionArtifactPoint: Int
)