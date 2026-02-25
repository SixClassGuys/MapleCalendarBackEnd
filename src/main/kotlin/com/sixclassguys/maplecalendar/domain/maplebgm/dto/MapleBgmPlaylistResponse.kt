package com.sixclassguys.maplecalendar.domain.maplebgm.dto

data class MapleBgmPlaylistResponse(
    val id: Long,
    val name: String,
    val description: String?,
    val isPublic: Boolean,
    val bgms: List<MapleBgmResponse>
)