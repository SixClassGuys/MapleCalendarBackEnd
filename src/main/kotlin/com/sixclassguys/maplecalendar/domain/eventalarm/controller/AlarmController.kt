package com.sixclassguys.maplecalendar.domain.eventalarm.controller

import com.sixclassguys.maplecalendar.domain.event.dto.EventResponse
import com.sixclassguys.maplecalendar.domain.eventalarm.dto.AlarmRequest
import com.sixclassguys.maplecalendar.domain.eventalarm.service.AlarmService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/alarms")
class AlarmController(
    private val alarmService: AlarmService
) {

    @PostMapping("/event")
    fun saveEventAlarm(
        @AuthenticationPrincipal userDetails: UserDetails,
        @RequestBody request: AlarmRequest
    ): ResponseEntity<EventResponse> {
        val updatedEvent = alarmService.saveOrUpdateAlarm(userDetails.username, request)

        return ResponseEntity.ok(updatedEvent)
    }

    // 💡 알람 스위치 토글 전용 API
    @PatchMapping("/toggle/{eventId}")
    fun toggleAlarm(
        @AuthenticationPrincipal userDetails: UserDetails,
        @PathVariable eventId: Long
    ): ResponseEntity<EventResponse> {
        val updatedEvent = alarmService.toggleAlarmStatus(userDetails.username, eventId)
        return ResponseEntity.ok(updatedEvent)
    }
}