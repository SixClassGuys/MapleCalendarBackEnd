package com.sixclassguys.maplecalendar.infrastructure.external

import com.sixclassguys.maplecalendar.global.config.NexonProperties
import com.sixclassguys.maplecalendar.infrastructure.external.dto.EventNotice
import com.sixclassguys.maplecalendar.infrastructure.external.dto.EventNoticeResponse
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class NexonApiClient(
    private val nexonProperties: NexonProperties,
    private val restTemplate: RestTemplate
) {

    fun getRecent20Events(): List<EventNotice> {
        // 엔드포인트 설정
        val url = "${nexonProperties.baseUrl}/notice-event"

        // 헤더에 API Key 설정
        val headers = HttpHeaders().apply {
            set("x-nxopen-api-key", nexonProperties.key)
        }
        val entity = HttpEntity<Unit>(headers)

        // API 호출
        val response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            EventNoticeResponse::class.java
        )

        // body가 null일 경우 빈 리스트 반환, 데이터가 있다면 최근 20개 추출
        return response.body?.eventNotice?.take(20) ?: emptyList()
    }
}