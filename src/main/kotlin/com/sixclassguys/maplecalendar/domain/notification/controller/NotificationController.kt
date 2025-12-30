package com.sixclassguys.maplecalendar.domain.notification.controller

import com.sixclassguys.maplecalendar.domain.notification.dto.TokenRequest
import com.sixclassguys.maplecalendar.domain.notification.service.NotificationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/notifications")
class NotificationController(
    private val notificationService: NotificationService
) {

    @PostMapping("/tokens")
    fun registerToken(@RequestBody request: TokenRequest): ResponseEntity<Unit> {
        notificationService.registerToken(request)

        return ResponseEntity.ok().build()
    }
}