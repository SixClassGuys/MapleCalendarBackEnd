package com.sixclassguys.maplecalendar.domain.auth.dto

data class AppleUserInfo(
    val sub: String,
    val email: String?,
    val name: String? = null
)