package com.sixclassguys.maplecalendar.domain.boss.repository

import com.sixclassguys.maplecalendar.domain.boss.entity.BossPartyBoardImage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BossPartyBoardImageRepository : JpaRepository<BossPartyBoardImage, Long> {
    
    fun findAllByBossPartyBoardId(boardId: Long): List<BossPartyBoardImage>
}
