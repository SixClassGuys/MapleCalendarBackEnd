package com.sixclassguys.maplecalendar.domain.characterInformation.entity

import com.sixclassguys.maplecalendar.domain.member.entity.Member
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "character_information")
class CharacterInformation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "representative_ocid")
    var representativeOcid: String? = null,

    @Column(name = "overall_ranking")
    var overallRanking: Int? = null,

    @Column(name = "server_ranking")
    var serverRanking: Int? = null,

    @Column(name = "popularity")
    var popularity: Int? = null,

    @Column(name = "dojang_best_floor")
    var dojangBestFloor: Int? = null,

    @Column(name = "union_level")
    var unionLevel: Int? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, unique = true)
    var member: Member, // non-nullable 추천
)
