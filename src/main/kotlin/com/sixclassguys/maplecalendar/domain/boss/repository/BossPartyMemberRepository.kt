package com.sixclassguys.maplecalendar.domain.boss.repository

import com.sixclassguys.maplecalendar.domain.boss.entity.BossPartyMember
import com.sixclassguys.maplecalendar.domain.boss.enums.JoinStatus
import com.sixclassguys.maplecalendar.domain.boss.enums.PartyRole
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface BossPartyMemberRepository : JpaRepository<BossPartyMember, Long> {

    fun existsByBossPartyIdAndCharacterId(bossPartyId: Long, characterId: Long): Boolean

    @Query("SELECT m FROM BossPartyMember m JOIN FETCH m.character c WHERE m.bossParty.id = :bossPartyId")
    fun findAllByBossPartyId(bossPartyId: Long): List<BossPartyMember>

    @Query("""
    SELECT m FROM BossPartyMember m 
    JOIN FETCH m.character c
    JOIN FETCH c.member mem
    WHERE m.bossParty.id IN :partyIds 
    AND m.joinStatus = :joinStatus
    """)
    fun findAllWithMemberByPartyIds(
        @Param("partyIds") partyIds: List<Long>,
        @Param("joinStatus") joinStatus: JoinStatus
    ): List<BossPartyMember>

    @Query("""
    SELECT m FROM BossPartyMember m 
    JOIN FETCH m.character c
    JOIN FETCH c.member mem
    LEFT JOIN FETCH mem.tokens
    WHERE m.bossParty.id = :partyId 
    AND m.joinStatus = :joinStatus
    """)
    fun findAllWithMemberAndTokensByPartyId(
        @Param("partyId") partyId: Long,
        @Param("joinStatus") joinStatus: JoinStatus
    ): List<BossPartyMember>

    // 스케줄 교집합 연산용: 특정 파티의 '승인된' 멤버들만 캐릭터 정보와 함께 Fetch Join으로 조회
    @Query("""
        SELECT m FROM BossPartyMember m 
        JOIN FETCH m.character c 
        WHERE m.bossParty.id = :partyId 
        AND m.joinStatus = :joinStatus
    """)
    fun findAllByBossPartyIdAndJoinStatus(
        @Param("partyId") partyId: Long,
        @Param("joinStatus") joinStatus: JoinStatus
    ): List<BossPartyMember>

    @Query("""
        SELECT m FROM BossPartyMember m 
        JOIN FETCH m.character c
        JOIN FETCH c.member mem
        WHERE m.bossParty.id = :partyId 
        AND mem.email = :email
    """)
    fun findByBossPartyIdAndCharacterMemberEmailAcceptedAndInvited(
        @Param("partyId") partyId: Long,
        @Param("email") email: String
    ): BossPartyMember?

    @Query("""
        SELECT m FROM BossPartyMember m 
        JOIN FETCH m.character c
        JOIN FETCH c.member mem
        WHERE m.bossParty.id = :partyId 
        AND mem.email = :email
        AND m.joinStatus = 'INVITED'
    """)
    fun findByBossPartyIdAndCharacterMemberEmailInvited(
        @Param("partyId") partyId: Long,
        @Param("email") email: String
    ): BossPartyMember?

    @Query("""
        SELECT m FROM BossPartyMember m 
        JOIN FETCH m.character c
        JOIN FETCH c.member mem
        WHERE m.bossParty.id = :partyId 
        AND mem.email = :email
        AND m.joinStatus = 'ACCEPTED'
    """)
    fun findByBossPartyIdAndCharacterMemberEmail(
        @Param("partyId") partyId: Long,
        @Param("email") email: String
    ): BossPartyMember?

    // 특정 캐릭터 조회 (fetch join 포함)
    @Query("""
        SELECT m FROM BossPartyMember m
        JOIN FETCH m.character c
        JOIN FETCH c.member mem
        WHERE m.bossParty.id = :partyId
        AND c.id = :characterId
    """)
    fun findByBossPartyIdAndCharacterId(
        @Param("partyId") bossPartyId: Long,
        @Param("characterId") characterId: Long
    ): BossPartyMember?

    // 특정 이메일 + Role 조회 (리더 권한 체크용)
    @Query("""
        SELECT m FROM BossPartyMember m
        JOIN FETCH m.character c
        JOIN FETCH c.member mem
        WHERE m.bossParty.id = :partyId
        AND mem.email = :email
        AND m.role = :role
    """)
    fun findByBossPartyIdAndCharacterMemberEmailAndRole(
        @Param("partyId") bossPartyId: Long,
        @Param("email") email: String,
        @Param("role") role: PartyRole
    ): BossPartyMember?

    @Modifying(clearAutomatically = true) // 벌크 연산 후 영속성 컨텍스트를 깔끔하게 비워줌
    @Query("""
        UPDATE BossPartyMember m 
        SET m.availableSlots = :emptySlots 
        WHERE m.keepNextWeek = false
    """)
    fun resetWeeklySchedules(@Param("emptySlots") emptySlots: String): Int

    /**
     * 시간대가 미확정된 파티의 파티장들의 이메일(또는 Member ID) 목록을 한 번에 가져오는 쿼리문
     */
    @Query("""
    SELECT DISTINCT bpm.character.member.id 
    FROM BossPartyMember bpm 
    WHERE bpm.role = 'LEADER' 
      AND bpm.bossParty.isDeleted = false
      AND bpm.bossParty.id NOT IN (
          SELECT bpat.bossPartyId 
          FROM BossPartyAlarmTime bpat 
          WHERE bpat.isSent = false
      )
""")
    fun findLeaderMemberIdsWithNoReservedAlarm(): List<Long>
}