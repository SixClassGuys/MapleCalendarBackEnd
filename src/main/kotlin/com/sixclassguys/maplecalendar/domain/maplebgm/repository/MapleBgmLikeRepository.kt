package com.sixclassguys.maplecalendar.domain.maplebgm.repository

import com.sixclassguys.maplecalendar.domain.maplebgm.entity.MapleBgm
import com.sixclassguys.maplecalendar.domain.maplebgm.entity.MapleBgmLike
import com.sixclassguys.maplecalendar.domain.member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MapleBgmLikeRepository : JpaRepository<MapleBgmLike, MapleBgm> {

    // 특정 멤버가 조회하려는 BGM 리스트(ID들) 중에서 누른 좋아요/싫어요 내역을 한 번에 가져옴
    fun findAllByMemberAndMapleBgmIdIn(member: Member, mapleBgmIds: List<Long>): List<MapleBgmLike>

    // (보너스) 특정 곡에 대한 사용자의 상태만 딱 하나 찾을 때 사용
    fun findByMemberAndMapleBgm(member: Member, mapleBgm: MapleBgm): MapleBgmLike?
}