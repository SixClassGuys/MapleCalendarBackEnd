package com.sixclassguys.maplecalendar.domain.maplebgm.entity

import com.sixclassguys.maplecalendar.domain.member.entity.Member
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "maple_bgm_playlists")
class MapleBgmPlaylist(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val member: Member,

    @OneToMany(mappedBy = "playlist", cascade = [CascadeType.ALL])
    val items: MutableList<MapleBgmPlaylistItem> = mutableListOf(),

    @Column(nullable = false)
    var name: String, // 플레이리스트 이름 (예: 사냥할 때 듣는 곡)

    @Column(nullable = false)
    var isPublic: Boolean = false, // 공개 여부 (기본값 비공개)

    var description: String? = null, // 설명

    @Column(nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    var updatedAt: LocalDateTime = LocalDateTime.now()
)