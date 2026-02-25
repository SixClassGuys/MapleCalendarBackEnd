package com.sixclassguys.maplecalendar.domain.maplebgm.entity

import com.sixclassguys.maplecalendar.domain.maplebgm.enum.MapleBgmLikeStatus
import com.sixclassguys.maplecalendar.domain.member.entity.Member
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

@Entity
@Table(
    name = "maple_bgm_likes",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["member_id", "maple_bgm_id"]) // 한 유저는 곡당 하나의 상태만 가짐
    ]
)
class MapleBgmLike(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val member: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maple_bgm_id")
    val mapleBgm: MapleBgm,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: MapleBgmLikeStatus
)