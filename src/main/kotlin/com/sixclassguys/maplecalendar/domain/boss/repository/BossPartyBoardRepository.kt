package com.sixclassguys.maplecalendar.domain.boss.repository

import com.sixclassguys.maplecalendar.domain.boss.entity.BossPartyBoard
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface BossPartyBoardRepository : JpaRepository<BossPartyBoard, Long>{

    // 보스파티 객체 기준으로 게시글 조회 (생성일 기준 정렬)
//    fun findAllByBossPartyIdOrderByCreatedAtDesc(bossPartyId: Long): List<BossPartyBoard>

    // 보스파티 게시글 조회 (캐릭터 정보 Fetch Join)
    @Query("""
        SELECT b FROM BossPartyBoard b
        JOIN FETCH b.character c
        JOIN FETCH c.member m
        WHERE b.bossParty.id = :partyId
        ORDER BY b.createdAt DESC
    """)
    fun findAllByBossPartyId(
        @Param("partyId") partyId: Long,
        pageable: Pageable
    ): Slice<BossPartyBoard>

    fun findByIdAndBossPartyId(boardId: Long, partyId: Long): BossPartyBoard?
}