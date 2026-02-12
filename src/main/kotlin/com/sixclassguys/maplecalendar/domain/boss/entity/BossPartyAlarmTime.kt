package com.sixclassguys.maplecalendar.domain.boss.entity

import com.sixclassguys.maplecalendar.domain.boss.enums.RegistrationMode
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "boss_party_alarm_time")
class BossPartyAlarmTime(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    // 변경 포인트: mappingId 대신 bossPartyId를 참조
    @Column(name = "boss_party_id", nullable = false)
    val bossPartyId: Long,

    @Column(nullable = false)
    val alarmTime: LocalDateTime,

    @Enumerated(EnumType.STRING)
    @Column(name = "registration_mode", nullable = false)
    val registrationMode: RegistrationMode,

    @Column(nullable = false, length = 500)
    val message: String,

    @Column(nullable = false)
    var isSent: Boolean = false // 이 알람이 큐에 들어갔거나 처리되었는지 확인용
)