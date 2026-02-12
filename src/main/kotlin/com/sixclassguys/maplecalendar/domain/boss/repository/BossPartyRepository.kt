package com.sixclassguys.maplecalendar.domain.boss.repository

import com.sixclassguys.maplecalendar.domain.boss.dto.BossPartyResponse
import com.sixclassguys.maplecalendar.domain.boss.entity.BossParty
import com.sixclassguys.maplecalendar.domain.boss.enums.JoinStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface BossPartyRepository : JpaRepository<BossParty, Long>{
    @Query("""
        SELECT bp
        FROM BossParty bp
        JOIN MemberBossPartyMapping mbpm
            ON bp.id = mbpm.bossPartyId
        WHERE mbpm.memberId = :memberId AND bp.isDeleted = false
    """)
    fun findAllByMemberId(memberId: Long): List<BossParty>

    @Query("""
    SELECT p, bm, 
           COALESCE(m.isPartyAlarmEnabled, true), 
           COALESCE(m.isChatAlarmEnabled, true)
    FROM BossParty p
    JOIN BossPartyMember bm ON p.id = bm.bossParty.id
    LEFT JOIN MemberBossPartyMapping m ON p.id = m.bossPartyId AND m.memberId = :memberId
    WHERE bm.character.member.id = :memberId 
      AND bm.joinStatus IN :statuses
      AND p.isDeleted = false
""")
    fun findAllPartiesByMemberId(
        @Param("memberId") memberId: Long,
        @Param("statuses") statuses: List<JoinStatus>
    ): List<Array<Any>>

    @Query("""
        SELECT p 
        FROM BossParty p 
        JOIN FETCH p.members m 
        JOIN FETCH m.character c 
        WHERE p.id = :partyId AND p.isDeleted = false
    """)
    fun findDetailById(@Param("partyId") partyId: Long): BossParty?

    fun findByIdAndIsDeletedFalse(id: Long): BossParty?
}
