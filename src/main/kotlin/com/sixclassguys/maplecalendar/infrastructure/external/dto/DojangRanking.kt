package com.sixclassguys.maplecalendar.infrastructure.external.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class DojangRanking(
    @JsonProperty("date")
    val date: String?,

    @JsonProperty("ranking")
    val rank: Int?,

    @JsonProperty("character_name")
    val characterName: String?,

    @JsonProperty("world_name")
    val worldName: String?,

    @JsonProperty("class_name")
    val className: String?,

    @JsonProperty("sub_class_name")
    val subClassName: String?,

    @JsonProperty("character_level")
    val characterLevel: Int?,

    @JsonProperty("dojang_floor")
    val dojangFloor: Int?,

    @JsonProperty("dojang_time_record")
    val dojangTimeRecord: Int
)