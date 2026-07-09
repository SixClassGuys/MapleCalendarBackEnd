package com.sixclassguys.maplecalendar.domain.boss.entity

import com.sixclassguys.maplecalendar.domain.boss.enums.JoinStatus
import com.sixclassguys.maplecalendar.domain.boss.enums.PartyRole
import com.sixclassguys.maplecalendar.domain.character.entity.MapleCharacter
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "boss_party_member",
    uniqueConstraints = [UniqueConstraint(columnNames = ["boss_party_id", "character_id"])]
)
class BossPartyMember(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boss_party_id", nullable = false)
    val bossParty: BossParty,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", nullable = false)
    val character: MapleCharacter,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    var role: PartyRole,

    // 파티원별 보스 출발 가능 시간대를 나타내는 504개의 0과 1로 이루어진 비트마스크 문자열 추가
    @Column(name = "available_slots", length = 504, nullable = false, columnDefinition = "varchar(504) default '0'")
    var availableSlots: String = "0".repeat(504),

    // 선택한 시간대 정보 기억 여부를 나타내는 상태 추가
    @Column(name = "keep_next_week", nullable = false)
    var keepNextWeek: Boolean = false,

    @Enumerated(EnumType.STRING)
    @Column(name = "join_status")
    var joinStatus: JoinStatus? = null,

    @Column(name = "joined_at")
    var joinedAt: LocalDateTime = LocalDateTime.now()
) {

    // 비즈니스 메서드: 스케줄 업데이트
    fun updateSchedule(newSlots: String, keep: Boolean) {
        require(newSlots.length == 504) { "스케줄 데이터는 정확히 504개여야 합니다." }
        this.availableSlots = newSlots
        this.keepNextWeek = keep
    }

    // 비즈니스 메서드: 목요일 00시 초기화용
    fun resetScheduleIfRequired() {
        if (!this.keepNextWeek) {
            this.availableSlots = "0".repeat(504)
        }
    }
}