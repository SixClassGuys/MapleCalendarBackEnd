package com.sixclassguys.maplecalendar.domain.boss.controller

import com.sixclassguys.maplecalendar.domain.boss.dto.BossPartyAlarmTimeResponse
import com.sixclassguys.maplecalendar.domain.boss.dto.BossPartyChatMessageResponse
import com.sixclassguys.maplecalendar.domain.boss.dto.BossPartyCreateRequest
import com.sixclassguys.maplecalendar.domain.boss.dto.BossPartyCreateResponse
import com.sixclassguys.maplecalendar.domain.boss.dto.BossPartyMemberResponse
import com.sixclassguys.maplecalendar.domain.boss.dto.BossPartyResponse
import com.sixclassguys.maplecalendar.domain.boss.entity.BossParty
import com.sixclassguys.maplecalendar.domain.boss.entity.BossPartyAlarmTime
import com.sixclassguys.maplecalendar.domain.boss.service.BossPartyService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.security.core.userdetails.User
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@Tag(name = "Boss Party API", description = "보스 파티 관련 정보 조회 API")
@RestController
@RequestMapping("/api/boss-parties")
class BossPartyController(
    private val bossPartyService: BossPartyService,
) {

    @PostMapping
    fun create(@RequestBody @Valid req: BossPartyCreateRequest): ResponseEntity<BossPartyCreateResponse> {
        val partyId = bossPartyService.createParty(req)
        return ResponseEntity.ok(BossPartyCreateResponse(partyId))
    }

    // 나중에 jwt에 memberId 넣는식으로 고쳐서 쓰는게 편할듯
    @Operation(summary = "회원이 속한 보스 파티 조회", description = "로그인한 회원의 이메일을 기준으로 가입된 모든 보스 파티 목록을 조회합니다.")
    @GetMapping
    fun getBossPartiesByMemberId(
    ): ResponseEntity<List<BossPartyResponse>> {
        val authentication = SecurityContextHolder.getContext().authentication

        // principal이 User 객체이므로 username이 이메일
        val email = (authentication?.principal as User).username

        val bossParties = bossPartyService.getBossPartiesByMemberEmail(email)
        return ResponseEntity.ok(bossParties)
    }

    @Operation(
        summary = "보스 파티 알람 시간 조회",
        description = "보스 파티 ID를 기반으로 해당 파티에 설정된 알람 시간 목록을 조회합니다."
    )
    @GetMapping("/{bossPartyId}/alarm-times")
    fun getBossPartyAlarmTimes(
        @Parameter(description = "조회할 보스 파티의 ID", required = true)
        @PathVariable bossPartyId: Long
    ): List<BossPartyAlarmTimeResponse> {
        return bossPartyService.getAlarmTimesByBossPartyId(bossPartyId)
    }

    @Operation(
        summary = "보스 파티 수락된 멤버 조회",
        description = "보스 파티 ID를 기반으로 해당 파티에 참여를 수락한 멤버 목록을 조회합니다."
    )
    @GetMapping("/{bossPartyId}/members/accepted")
    fun getAcceptedMembers(
        @Parameter(description = "조회할 보스 파티의 ID", required = true)
        @PathVariable bossPartyId: Long
    ): List<BossPartyMemberResponse> {
        return bossPartyService.getAcceptedMembersByBossPartyId(bossPartyId)
    }

    @Operation(
        summary = "보스 파티 채팅 메시지 조회",
        description = "보스 파티 ID를 기반으로 해당 파티에 등록된 채팅 메시지 목록을 조회합니다."
    )

    @GetMapping("/{bossPartyId}/chat-messages")
    fun getChatMessages(
        @Parameter(description = "조회할 보스 파티의 ID", required = true)
        @PathVariable bossPartyId: Long
    ): List<BossPartyChatMessageResponse> {
        return bossPartyService.getMessagesByBossPartyId(bossPartyId)
    }


}