package com.sixclassguys.maplecalendar.domain.maplebgm.repository

import com.sixclassguys.maplecalendar.domain.maplebgm.entity.MapleBgmPlaylist
import com.sixclassguys.maplecalendar.domain.member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MapleBgmPlaylistRepository : JpaRepository<MapleBgmPlaylist, Long> {

    fun findAllByMemberOrderByCreatedAtDesc(member: Member): List<MapleBgmPlaylist>
}