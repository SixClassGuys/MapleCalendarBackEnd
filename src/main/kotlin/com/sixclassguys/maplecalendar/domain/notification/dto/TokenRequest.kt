package com.sixclassguys.maplecalendar.domain.notification.dto

data class TokenRequest(
    val token: String,
    val platform: Platform
)

enum class Platform {
    ANDROID, IOS
}