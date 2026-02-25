package com.sixclassguys.maplecalendar.domain.maplebgm.dto

data class CreateMapleBgmPlaylistRequest(
    val name: String,
    val description: String,
    val isPublic: Boolean
)