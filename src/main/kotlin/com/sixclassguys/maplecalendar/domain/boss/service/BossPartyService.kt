package com.sixclassguys.maplecalendar.domain.boss.service

import com.sixclassguys.maplecalendar.domain.boss.dto.BossPartyAlarmTimeResponse
import com.sixclassguys.maplecalendar.domain.boss.dto.BossPartyBoardResponse
import com.sixclassguys.maplecalendar.domain.boss.dto.BossPartyChatMessageResponse
import com.sixclassguys.maplecalendar.domain.boss.dto.BossPartyCreateRequest
import com.sixclassguys.maplecalendar.domain.boss.dto.BossPartyMemberResponse
import com.sixclassguys.maplecalendar.domain.boss.dto.BossPartyResponse
import com.sixclassguys.maplecalendar.domain.boss.entity.BossParty
import com.sixclassguys.maplecalendar.domain.boss.entity.BossPartyAlarmTime
import com.sixclassguys.maplecalendar.domain.boss.entity.BossPartyMember
import com.sixclassguys.maplecalendar.domain.boss.enums.JoinStatus
import com.sixclassguys.maplecalendar.domain.boss.enums.PartyRole
import com.sixclassguys.maplecalendar.domain.boss.repository.BossPartyAlarmTimeRepository
import com.sixclassguys.maplecalendar.domain.boss.repository.BossPartyBoardRepository
import com.sixclassguys.maplecalendar.domain.boss.repository.BossPartyChatMessageRepository
import com.sixclassguys.maplecalendar.domain.boss.repository.BossPartyMemberRepository
import com.sixclassguys.maplecalendar.domain.boss.repository.BossPartyRepository
import com.sixclassguys.maplecalendar.domain.character.repository.MapleCharacterRepository
import com.sixclassguys.maplecalendar.domain.member.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class BossPartyService(
    private val bossPartyRepository: BossPartyRepository,
    private val bossPartyMemberRepository: BossPartyMemberRepository,
    private val mapleCharacterRepository: MapleCharacterRepository,
    private val memberRepository: MemberRepository,
    private val bossPartyAlarmTimeRepository: BossPartyAlarmTimeRepository,
    private val bossPartyChatMessageRepository: BossPartyChatMessageRepository,
    private val bossPartyBoardRepository: BossPartyBoardRepository,
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
//            bossPartyId = savedParty.id,
//            characterId = character.id!!,
            bossParty = savedParty,
            character = character!!,
            role = PartyRole.LEADER,
            joinStatus = JoinStatus.ACCEPTED,
            joinedAt = LocalDateTime.now()
        )

        bossPartyMemberRepository.save(leader)

        return savedParty.id
    }

    @Transactional
    fun getBossPartiesByMemberEmail(memberEmail: String): List<BossPartyResponse> {
        val member = memberRepository.findByEmail(memberEmail)
            ?: throw NoSuchElementException("해당 이메일의 멤버가 없습니다.")

        return bossPartyRepository.findAllByMemberId(member.id!!)
            .map {
                BossPartyResponse(
                    id = it.id,
                    title = it.title,
                    description = it.description,
                    boss = it.boss.name,
                    difficulty = it.difficulty.name,
                    createdAt = it.createdAt
                )
            }
    }

    @Transactional
    fun getAlarmTimesByBossPartyId(bossPartyId: Long): List<BossPartyAlarmTimeResponse> {

        val alarmTimes = bossPartyAlarmTimeRepository.findByBossPartyId(bossPartyId)

        return alarmTimes.map {
            BossPartyAlarmTimeResponse(
                id = it.id,
                alarmTime = it.alarmTime,
                message = it.message,
                isSent = it.isSent
            )
        }
    }


    @Transactional
    fun getAcceptedMembersByBossPartyId(bossPartyId: Long): List<BossPartyMemberResponse> {

        val bossPartyMembers = bossPartyMemberRepository.findAllByBossPartyIdAndJoinStatus(
            bossPartyId,
            JoinStatus.ACCEPTED // 수락 상태만 조회
        )

        return bossPartyMembers.map {
            BossPartyMemberResponse(
                id = it.id,
                character = it.character,
                role = it.role,
                joinStatus = it.joinStatus!!,
                joinedAt = it.joinedAt
            )
        }
    }


    @Transactional(readOnly = true)
    fun getMessagesByBossPartyId(bossPartyId: Long): List<BossPartyChatMessageResponse> {

        val messages = bossPartyChatMessageRepository.findAllByBossPartyIdOrderByCreatedAtAsc(bossPartyId)

        return messages.map {
            BossPartyChatMessageResponse(
                id = it.id,
                characterId = it.character.id!!,
                characterName = it.character.characterName,
                content = it.content,
                messageType = it.messageType,
                createdAt = it.createdAt
            )
        }
    }
}