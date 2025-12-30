package com.sixclassguys.maplecalendar.domain.notification.repository

import com.sixclassguys.maplecalendar.domain.notification.entity.NotificationToken
import org.springframework.data.jpa.repository.JpaRepository

interface NotificationTokenRepository : JpaRepository<NotificationToken, Long> {

    fun findByToken(token: String): NotificationToken?
}