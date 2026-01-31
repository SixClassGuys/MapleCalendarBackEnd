package com.sixclassguys.maplecalendar.domain.boss.controller

import com.sixclassguys.maplecalendar.domain.boss.dto.BossPartyCreateRequest
import com.sixclassguys.maplecalendar.domain.boss.dto.BossPartyCreateResponse
import com.sixclassguys.maplecalendar.domain.boss.service.BossPartyService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}