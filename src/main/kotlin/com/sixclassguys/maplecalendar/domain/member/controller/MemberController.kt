package com.sixclassguys.maplecalendar.domain.member.controller

import com.sixclassguys.maplecalendar.domain.auth.dto.LoginResponse
import com.sixclassguys.maplecalendar.domain.member.dto.RepresentativeOcidRequest
import com.sixclassguys.maplecalendar.domain.member.service.MemberService
import com.sixclassguys.maplecalendar.domain.notification.dto.FcmTokenRequest
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/member")
class MemberController(
    private val memberService: MemberService
) {

    @PostMapping("/myinfo")
    fun getMyInfo(
        @AuthenticationPrincipal userDetails: UserDetails,
        @RequestBody request: FcmTokenRequest
    ): ResponseEntity<LoginResponse> {
        val response = memberService.getMyInfo(userDetails.username, request.token, request.platform)

        return ResponseEntity.ok(response)
    }

    /*
    @PatchMapping("/representative")
    fun setRepresentative(
        @AuthenticationPrincipal userDetails: UserDetails,
        @RequestBody request: RepresentativeOcidRequest
    ): ResponseEntity<Unit> {
        memberService.updateRepresentativeCharacter(apiKey, request.ocid)

        return ResponseEntity.noContent().build() // 성공 시 204 No Content 반환
    }
    */

    @PatchMapping("/alarm-status")
    fun updateAlarmStatus(
        @AuthenticationPrincipal userDetails: UserDetails,
    ): ResponseEntity<Boolean> {
        val isGlobalAlarmEnabled = memberService.updateGlobalAlarmStatus(userDetails.username)

        return ResponseEntity.ok(isGlobalAlarmEnabled)
    }
}