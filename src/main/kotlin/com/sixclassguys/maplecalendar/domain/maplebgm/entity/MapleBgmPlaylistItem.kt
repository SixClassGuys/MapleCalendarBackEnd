package com.sixclassguys.maplecalendar.domain.maplebgm.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "maple_bgm_playlist_items")
class MapleBgmPlaylistItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maple_bgm_playlist_id")
    val playlist: MapleBgmPlaylist,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maple_bgm_id")
    val bgm: MapleBgm,

    @Column(nullable = false)
    var sortOrder: Int = 0, // 곡 순서

    @Column(nullable = false)
    val addedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean = false,

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null
)