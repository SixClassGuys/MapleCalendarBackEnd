package com.sixclassguys.maplecalendar.domain.member.service

import com.sixclassguys.maplecalendar.domain.auth.dto.LoginResponse
import com.sixclassguys.maplecalendar.domain.character.repository.MapleCharacterRepository
import com.sixclassguys.maplecalendar.domain.member.entity.Member
import com.sixclassguys.maplecalendar.domain.member.repository.MemberRepository
import com.sixclassguys.maplecalendar.domain.notification.dto.Platform
import com.sixclassguys.maplecalendar.domain.notification.dto.FcmTokenRequest
import com.sixclassguys.maplecalendar.domain.notification.service.NotificationService
import com.sixclassguys.maplecalendar.global.exception.MemberNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val notificationService: NotificationService,
    private val memberRepository: MemberRepository,
    private val mapleCharacterRepository: MapleCharacterRepository
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Transactional
    fun getMyInfo(userEmail: String, fcmToken: String, platform: Platform): LoginResponse {
        // 1. 유저 조회
        val member = memberRepository.findByEmail(userEmail)
            ?: throw MemberNotFoundException()

        // 2. FCM 토큰 업데이트 (로그인 로직과 동일하게 유지)
        try {
            notificationService.registerToken(
                request = FcmTokenRequest(token = fcmToken, platform = platform),
                memberId = member.id
            )
        } catch (e: Exception) {
            // 알림 토큰 업데이트 실패가 로그인 흐름 전체를 방해하지 않도록 예외 처리만 수행
            log.error("자동 로그인 중 FCM 토큰 업데이트 실패: ${e.message}")
        }

        // 3. 대표 캐릭터 OCID가 있는지 확인
        val representativeOcid = member.representativeOcid

        // 4. 대표 캐릭터 정보 가져오기 (DB 또는 넥슨 API)
        val characterEntity = representativeOcid?.let {
            mapleCharacterRepository.findByOcid(it)
        }

        // 5. (선택사항) 실시간 데이터 갱신이 필요하다면 여기서 넥슨 API를 추가 호출
        // 일단은 LoginResponse.fromEntity를 사용하여 DB에 저장된 정보를 반환
        return LoginResponse.fromEntity(
            member = member,
            character = characterEntity,
            accessToken = "", // 이미 인증된 상태이므로 빈 값 전달 (클라이언트는 기존 토큰 유지)
            refreshToken = "",
            isNewMember = false
        )
    }

    @Transactional
    fun updateGlobalAlarmStatus(userEmail: String): Boolean {
        // 1. 유저 조회
        val member = memberRepository.findByEmail(userEmail)
            ?: throw MemberNotFoundException()

        member.isGlobalAlarmEnabled = !member.isGlobalAlarmEnabled
        // Dirty Checking으로 자동 저장

        return member.isGlobalAlarmEnabled
    }

    // Auth 용도로 쓰일 기본 조회 함수
    fun findByEmail(email: String): Member? = memberRepository.findByEmail(email)

    // 유저 존재 여부만 확인하는 경우 (로그인/가입 시)
    fun findByProviderAndProviderId(provider: String, providerId: String): Member? {
        return memberRepository.findByProviderAndProviderId (provider,providerId)
    }

//    @Transactional
//    fun updateRepresentativeCharacter(apiKey: String, ocid: String) {
//        // 1. API Key로 유저 조회
//        val member = getMemberByRawKey(apiKey)
//
//        // 2. 대표 캐릭터 OCID 업데이트
//        // JPA의 변경 감지(Dirty Checking) 기능으로 인해 데이터를 변경하면 트랜잭션 종료 시 자동 저장
//        member.representativeOcid = ocid
//    }
//
//
//    // 추후 Controller에 API 추가
//    @Transactional
//    fun updateMemberCharacterStyle(apiKey: String, action: String, emotion: String, weapon: String) {
//        val member = getMemberByRawKey(apiKey)
//
//        member.charAction = action
//        member.charEmotion = emotion
//        member.charWeaponMotion = weapon
//    }
}