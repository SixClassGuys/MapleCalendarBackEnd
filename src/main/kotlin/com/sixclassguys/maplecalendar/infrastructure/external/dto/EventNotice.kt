package com.sixclassguys.maplecalendar.infrastructure.external.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class EventNotice(
    @JsonProperty("notice_id")
    val noticeId: Long,

    @JsonProperty("title")
    val title: String,

    @JsonProperty("url")
    val url: String,

    @JsonProperty("thumbnail_url")
    val thumbnailUrl: String?,

    @JsonProperty("date")
    val date: String,

    @JsonProperty("date_event_start")
    val dateEventStart: String,

    @JsonProperty("date_event_end")
    val dateEventEnd: String
)