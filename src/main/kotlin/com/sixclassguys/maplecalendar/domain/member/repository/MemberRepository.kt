package com.sixclassguys.maplecalendar.domain.member.repository

import com.sixclassguys.maplecalendar.domain.member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<Member, Long> {

    // 이제 nexonApiKey 대신 해시값으로 찾습니다.
    // ✅ 새로운 구조에 맞는 조회 메서드
    fun findByProviderAndProviderId(provider: String, providerId: String): Member?

    // 이메일 중복 체크 등이 필요하다면
    fun findByEmail(email: String): Member?
}