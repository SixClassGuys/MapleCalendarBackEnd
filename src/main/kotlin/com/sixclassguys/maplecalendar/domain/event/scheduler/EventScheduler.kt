package com.sixclassguys.maplecalendar.domain.event.scheduler

import com.sixclassguys.maplecalendar.domain.event.service.EventService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class EventScheduler(
    private val eventService: EventService
) {

    // 매일 오전 10시 정각 실행 (초 분 시 일 월 요일)
    @Scheduled(cron = "30 0 10 * * *")
    fun scheduleEventUpdate() {
        eventService.refreshAndCheckEvents()
    }
}