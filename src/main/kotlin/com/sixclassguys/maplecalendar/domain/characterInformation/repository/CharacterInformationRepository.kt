package com.sixclassguys.maplecalendar.domain.characterInformation.repository

import com.sixclassguys.maplecalendar.domain.characterInformation.entity.CharacterInformation
import com.sixclassguys.maplecalendar.domain.member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CharacterInformationRepository : JpaRepository<CharacterInformation, Long> {

    fun findByMember(member: Member): CharacterInformation?
}