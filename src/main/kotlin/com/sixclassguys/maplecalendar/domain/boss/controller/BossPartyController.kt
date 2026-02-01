package com.sixclassguys.maplecalendar.domain.boss.controller

import com.sixclassguys.maplecalendar.domain.boss.dto.BossPartyCreateRequest
import com.sixclassguys.maplecalendar.domain.boss.dto.BossPartyCreateResponse
import com.sixclassguys.maplecalendar.domain.boss.handler.BossPartyChatWebSocketHandler
import com.sixclassguys.maplecalendar.domain.boss.service.BossPartyService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/boss-parties")
class BossPartyController(
    private val bossPartyService: BossPartyService,
    private val webSocketHandler: BossPartyChatWebSocketHandler
) {

    @PostMapping
    fun create(@RequestBody @Valid req: BossPartyCreateRequest): ResponseEntity<BossPartyCreateResponse> {
        val partyId = bossPartyService.createParty(req)
        return ResponseEntity.ok(BossPartyCreateResponse(partyId))
    }

    @DeleteMapping("/{messageId}")
    fun deleteMessage(
        @AuthenticationPrincipal userDetails: UserDetails, // Spring Security 인증 정보
        @PathVariable messageId: Long
    ): ResponseEntity<Unit> {
        // 1. DB 상태 변경 (isDeleted = true)
        val deletedMessage = bossPartyService.deleteMessage(messageId, userDetails.username)

        // 2. WebSocket으로 모든 파티원에게 "메시지 상태 변경" 알림 전송
        webSocketHandler.broadcastDelete(deletedMessage.bossParty.id, messageId)

        return ResponseEntity.ok().build()
    }
}