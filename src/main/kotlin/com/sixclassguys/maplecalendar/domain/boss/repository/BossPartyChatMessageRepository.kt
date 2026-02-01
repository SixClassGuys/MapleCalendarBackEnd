package com.sixclassguys.maplecalendar.domain.boss.repository

import com.sixclassguys.maplecalendar.domain.boss.entity.BossPartyChatMessage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BossPartyChatMessageRepository : JpaRepository<BossPartyChatMessage, Long> {

    // 시간순으로 가져오기
    fun findAllByBossPartyIdOrderByCreatedAtAsc(bossPartyId: Long): List<BossPartyChatMessage>
}