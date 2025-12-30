package com.sixclassguys.maplecalendar.domain.notification.service

import com.sixclassguys.maplecalendar.domain.notification.dto.TokenRequest
import com.sixclassguys.maplecalendar.domain.notification.entity.NotificationToken
import com.sixclassguys.maplecalendar.domain.notification.repository.NotificationTokenRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class NotificationService(
    private val repository: NotificationTokenRepository
) {

    fun registerToken(request: TokenRequest) {
        val existingToken = repository.findByToken(request.token)

        if (existingToken != null) {
            existingToken.platform = request.platform
            existingToken.lastRegisteredAt = LocalDateTime.now()
            // JPA의 Dirty Checking으로 인해, 별도의 save 호출 없이도 업데이트가 가능하다.
        } else {
            repository.save(
                NotificationToken(
                    token = request.token,
                    platform = request.platform
                )
            )
        }
    }
}