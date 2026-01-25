package com.sixclassguys.maplecalendar.global.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "google.oauth")
data class GoogleOAuthProperties(
    val clientIds: List<String>
)