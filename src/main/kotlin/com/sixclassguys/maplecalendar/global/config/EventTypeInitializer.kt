package com.sixclassguys.maplecalendar.global.config

import com.sixclassguys.maplecalendar.domain.event.entity.EventType
import com.sixclassguys.maplecalendar.domain.event.repository.EventTypeRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("!test")
class EventTypeInitializer(
    private val eventTypeRepository: EventTypeRepository
) : CommandLineRunner {

    // 🚀 핵심: vararg args: String으로 작성해야 합니다. (String? 아님)
    override fun run(vararg args: String) {
        val types = listOf(
            "펀치킹", "코인샵", "썬데이메이플", "보스", "프리미엄PC방",
            "챌린저스", "아이템버닝", "하이퍼버닝", "VIP사우나", "뉴네임옥션",
            "스페셜월드", "출석이벤트", "리마스터", "기타", "패스",
            "코디", "사냥"
        )

        types.forEach { name ->
            if (!eventTypeRepository.existsByName(name)) {
                eventTypeRepository.save(EventType(name = name))
                println("✅ EventType 초기화 완료: $name") // 로그 확인용
            }
        }
    }
}