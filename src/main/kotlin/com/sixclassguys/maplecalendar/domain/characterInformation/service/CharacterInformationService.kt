package com.sixclassguys.maplecalendar.domain.characterInformation.service

import com.sixclassguys.maplecalendar.domain.characterInformation.entity.CharacterInformation
import com.sixclassguys.maplecalendar.domain.member.service.MemberService
import com.sixclassguys.maplecalendar.domain.characterInformation.repository.CharacterInformationRepository
import com.sixclassguys.maplecalendar.infrastructure.external.NexonApiClient
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.ZoneId

@Service
@Transactional
class CharacterInformationService(
    private val memberService: MemberService,
    private val characterInformationRepository: CharacterInformationRepository,
    private val nexonApiClient: NexonApiClient
) {
    private val log = LoggerFactory.getLogger(javaClass)

    fun refreshCharacterInfo(apiKey: String) {

        // 1. Member 조회
        val member = memberService.getMemberByRawKey(apiKey)

        val ocid = member.representativeOcid
            ?: throw IllegalStateException("대표 캐릭터가 설정되지 않았습니다.")

        // 2. CharacterInformation 조회 or 생성
        val info = characterInformationRepository.findByMember(member)
            ?: CharacterInformation(
                member = member,
                representativeOcid = ocid
            )
        val todayKst = LocalDate.now(ZoneId.of("Asia/Seoul"))

        // 3. Nexon API 호출
        val overAllRanking = nexonApiClient.getOverAllRanking(apiKey, ocid, todayKst)
        val worldName = overAllRanking?.ranking[0]?.worldName
        val serverRanking = worldName?.let {
            nexonApiClient.getServerRanking(apiKey, ocid, todayKst, it)
        }
        val union = nexonApiClient.getUnionInfo(apiKey, ocid)
        val dojang = nexonApiClient.getDojangInfo(apiKey, ocid, todayKst)
        log.info("overAllRanking 정보: $overAllRanking")
        log.info("serverRanking 정보: $serverRanking")
        log.info("union 정보: $union")
        log.info("Dojang 정보: $dojang")
        // 4. 값 반영 (Dirty Checking)
        info.representativeOcid = ocid
        info.popularity = overAllRanking?.ranking[0]?.characterPopularity
        info.overallRanking = overAllRanking?.ranking[0]?.rank
        info.serverRanking = serverRanking?.ranking[0]?.rank
        info.unionLevel = union?.unionLevel
        info.dojangBestFloor = if (dojang?.ranking?.isNotEmpty() == true) {
            dojang.ranking[0].dojangFloor
        } else {
            0
        }

        // 5. 최초 생성이면 저장
        if (info.id == null) {
            characterInformationRepository.save(info)
        }
    }

    fun getCharacterInfoByApiKey(apiKey: String): CharacterInformation {
        val member = memberService.getMemberByRawKey(apiKey)
        return characterInformationRepository.findByMember(member)
            ?: throw IllegalStateException("캐릭터 정보가 존재하지 않습니다.")
    }
}
