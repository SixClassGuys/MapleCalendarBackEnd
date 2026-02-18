package com.sixclassguys.maplecalendar.domain.auth.dto

import com.sixclassguys.maplecalendar.domain.notification.dto.Platform

data class AuthAppleRequest(
    val provider: String,
    val idToken: String,
    val fcmToken: String,
    val platform: Platform
)