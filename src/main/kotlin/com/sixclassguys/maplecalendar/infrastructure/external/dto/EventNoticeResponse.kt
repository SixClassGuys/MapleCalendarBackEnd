package com.sixclassguys.maplecalendar.infrastructure.external.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class EventNoticeResponse(
    @JsonProperty("event_notice")
    val eventNotice: List<EventNotice>
)