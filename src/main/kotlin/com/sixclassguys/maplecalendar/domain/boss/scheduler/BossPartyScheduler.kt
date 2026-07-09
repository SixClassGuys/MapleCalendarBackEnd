package com.sixclassguys.maplecalendar.domain.boss.scheduler

import com.sixclassguys.maplecalendar.domain.boss.repository.BossPartyMemberRepository
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class BossPartyScheduler(
    private val bossPartyMemberRepository: BossPartyMemberRepository
) {

    private val log = LoggerFactory.getLogger(javaClass)
    private val emptySlots = "0".repeat(504)

    /**
     * 매주 목요일 00시 00분 00초에 실행되는 크론탭(Cron) 스케줄러
     */
    @Scheduled(cron = "0 0 0 * * THU")
    @Transactional
    fun resetSchedulesEveryThursday() {
        log.info("[배치 실행] 목요일 00:00 - 주간 보스 스케줄 초기화를 시작합니다.")

        val updatedCount = bossPartyMemberRepository.resetWeeklySchedules(emptySlots)

        log.info("[배치 완료] 총 ${updatedCount}명의 파티원 스케줄이 초기화되었습니다.")
    }
}