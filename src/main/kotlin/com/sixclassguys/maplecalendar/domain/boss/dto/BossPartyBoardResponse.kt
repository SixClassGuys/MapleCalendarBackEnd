package com.sixclassguys.maplecalendar.domain.boss.dto

import java.time.LocalDateTime

data class BossPartyBoardResponse(
    val id: Long,
    val characterId: Long,
    val characterName: String,
    val characterImage: String?,
    val characterClass: String,
    val characterLevel: Long,
    val content: String,
    val createdAt: LocalDateTime,
    val imageUrls: List<String>,   // 이미지 URL 목록
    val likeCount: Int,    // 좋아요 수
    val dislikeCount: Int,  // 싫어요 수
    val userLikeType: String? = null, // 현재 유저의 반응: "LIKE", "DISLIKE", null
    val isAuthor: Boolean = false // 작성자인지 여부
)
