package com.sixclassguys.maplecalendar.domain.notification.entity

import com.sixclassguys.maplecalendar.domain.notification.dto.Platform
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "notification_tokens")
class NotificationToken(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(unique = true, nullable = false)
    var token: String,

    @Enumerated(EnumType.STRING)
    var platform: Platform,

    var lastRegisteredAt: LocalDateTime = LocalDateTime.now()
)