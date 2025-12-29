package com.sixclassguys.maplecalendar.global.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "nexon.api")
data class NexonProperties(
    val key: String,
    val baseUrl: String
)