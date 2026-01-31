package com.sixclassguys.maplecalendar.domain.boss.service

import com.sixclassguys.maplecalendar.domain.boss.dto.BossPartyCreateRequest
import com.sixclassguys.maplecalendar.domain.boss.entity.BossParty
import com.sixclassguys.maplecalendar.domain.boss.entity.BossPartyMember
import com.sixclassguys.maplecalendar.domain.boss.enums.JoinStatus
import com.sixclassguys.maplecalendar.domain.boss.enums.PartyRole
import com.sixclassguys.maplecalendar.domain.boss.repository.BossPartyMemberRepository
import com.sixclassguys.maplecalendar.domain.boss.repository.BossPartyRepository
import com.sixclassguys.maplecalendar.domain.character.repository.MapleCharacterRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class BossPartyService(
    private val bossPartyRepository: BossPartyRepository,
    private val bossPartyMemberRepository: BossPartyMemberRepository,
    private val mapleCharacterRepository: MapleCharacterRepository,
) {

    @Transactional
    fun createParty(
        req: BossPartyCreateRequest
    ): Long {
        val character = mapleCharacterRepository.findById(req.characterId)
            .orElseThrow { IllegalArgumentException("Character not found") }

        val bossParty = BossParty(
            title = req.title,
            description = req.description,
            boss = req.boss,
            difficulty = req.difficulty
        )

        val savedParty = bossPartyRepository.save(bossParty)

        val leader = BossPartyMember(
            bossPartyId = savedParty.id,
            characterId = character.id!!,
            role = PartyRole.LEADER,
            joinStatus = JoinStatus.ACCEPTED,
            joinedAt = LocalDateTime.now()
        )

        bossPartyMemberRepository.save(leader)

        return savedParty.id
    }
}