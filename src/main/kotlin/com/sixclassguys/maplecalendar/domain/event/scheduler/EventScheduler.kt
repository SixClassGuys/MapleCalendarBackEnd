package com.sixclassguys.maplecalendar.domain.event.scheduler

import com.sixclassguys.maplecalendar.domain.event.service.EventService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class EventScheduler(
    private val eventService: EventService
) {
    // 매일 오전 10시 정각 실행 (초 분 시 일 월 요일)
    @Scheduled(cron = "5 * * * * *")
    fun scheduleEventUpdate() {
        eventService.refreshAndCheckEvents()
    }

    /* * 테스트를 위해 서버 기동 후 5초 뒤에 바로 한 번 실행해보고 싶다면?
     * @Scheduled(initialDelay = 5000, fixedDelay = Long.MAX_VALUE)
     * 를 임시로 붙여두면 바로 확인할 수 있습니다.
     */
}