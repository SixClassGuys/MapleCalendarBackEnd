package com.sixclassguys.maplecalendar.domain.maplebgm.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "maple_bgms")
class MapleBgm(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    var title: String,        // 곡 제목

    @Column(nullable = false)
    var fileName: String,     // 파일명 (예: title.mp3)

    @Column(nullable = false)
    var mapName: String,

    @Column(nullable = false)
    var region: String, // 지역 (예: 리스항구, 헤네시스)

    var description: String? = null, // 곡 설명

    @Column(name = "thumbnail_url")
    var thumbnailUrl: String? = null,

    @Column(nullable = false)
    var totalPlayCount: Long = 0, // 총 재생 횟수 추가

    @Column(nullable = false)
    var likeCount: Long = 0,       // 빠른 조회를 위한 좋아요 합계 (선택)

    @Column(nullable = false, name = "dislike_count") // [추가] 싫어요 카운트
    var dislikeCount: Long = 0,

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: java.time.LocalDateTime = java.time.LocalDateTime.now()
)