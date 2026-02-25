package com.sixclassguys.maplecalendar.domain.maplebgm.dto

import com.sixclassguys.maplecalendar.domain.maplebgm.enum.MapleBgmLikeStatus

data class MapleBgmResponse(
    val id: Long,
    val title: String,
    val audioUrl: String,
    val mapName: String,
    val region: String,
    val description: String?,
    val thumbnailUrl: String?,
    val likeCount: Long,
    val dislikeCount: Long,
    val likeStatus: MapleBgmLikeStatus?,
    val playCount: Long
)