package com.sixclassguys.maplecalendar.domain.boss.repository

import com.sixclassguys.maplecalendar.domain.boss.entity.BossPartyBoardLike
import com.sixclassguys.maplecalendar.domain.boss.enums.BoardLikeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BossPartyBoardLikeRepository : JpaRepository<BossPartyBoardLike, Long> {
    
    // 특정 게시글에 특정 유저의 좋아요 조회
    fun findByBossPartyBoardIdAndCharacterId(
        boardId: Long,
        characterId: Long
    ): BossPartyBoardLike?

    // 특정 게시글의 좋아요/싫어요 수 계산
    fun countByBossPartyBoardIdAndBoardLikeType(
        boardId: Long,
        likeType: BoardLikeType
    ): Long
}
