package com.sixclassguys.maplecalendar.domain.boss.entity

import com.sixclassguys.maplecalendar.domain.boss.enums.BossPartyChatMessageType
import com.sixclassguys.maplecalendar.domain.character.entity.MapleCharacter
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "boss_party_chat_message")
class BossPartyChatMessage(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    // BossParty 객체로 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boss_party_id", nullable = false)
    val bossParty: BossParty,

    // Character 객체로 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", nullable = false)
    val character: MapleCharacter,

    @Column(nullable = false, length = 500)
    val content: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val messageType: BossPartyChatMessageType,

    @Column(nullable = false)
    val isDeleted: Boolean = false,

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)
