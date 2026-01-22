package com.sixclassguys.maplecalendar.domain.util

import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

fun getZonedDateTime(): LocalDate {
    val seoulZone = ZoneId.of("Asia/Seoul")
    val now = ZonedDateTime.now(seoulZone)

    // 기준 시간 설정 (09:01:00)
    val criteriaTime = LocalTime.of(9, 1)

    // 현재 시간이 09:01:00 보다 이전인지 확인
    val isBeforeCriteria = now.toLocalTime().isBefore(criteriaTime)

    return if (isBeforeCriteria) {
        // 00:00:00 ~ 09:00:59 까지는 어제 날짜
        now.toLocalDate().minusDays(1)
    } else {
        // 09:01:00 부터는 오늘 날짜
        now.toLocalDate()
    }
}