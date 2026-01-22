package com.sixclassguys.maplecalendar.infrastructure.external.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class Ranking(
    @JsonProperty("date")
    val date: String,

    @JsonProperty("ranking")
    val rank: Int,

    @JsonProperty("character_name")
    val characterName: String,

    @JsonProperty("world_name")
    val worldName: String,

    @JsonProperty("class_name")
    val className: String,

    @JsonProperty("sub_class_name")
    val subClassName: String,

    @JsonProperty("character_level")
    val characterLevel: Int,

    @JsonProperty("character_exp")
    val characterExp: Long,

    @JsonProperty("character_popularity")
    val characterPopularity: Int,

    @JsonProperty("character_guildname")
    val characterGuildName: String?
)