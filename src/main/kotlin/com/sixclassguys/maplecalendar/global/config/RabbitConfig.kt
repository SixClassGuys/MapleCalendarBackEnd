package com.sixclassguys.maplecalendar.global.config

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.CustomExchange
import org.springframework.amqp.core.Queue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitConfig {

    // 1. 기존 지연 교환기 설정 유지
    @Bean
    fun delayedExchange(): CustomExchange {
        val args = mapOf("x-delayed-type" to "direct")
        return CustomExchange(DELAYED_EXCHANGE, "x-delayed-message", true, false, args)
    }

    // 2. 보스 파티 알람용 큐와 바인딩
    @Bean
    fun bossAlarmQueue(): Queue = Queue(BOSS_QUEUE)

    @Bean
    fun bossAlarmBinding(bossAlarmQueue: Queue, delayedExchange: CustomExchange): Binding {
        return BindingBuilder.bind(bossAlarmQueue).to(delayedExchange).with(BOSS_ROUTING_KEY).noargs()
    }

    // 3. 이벤트 종료 알람용 큐와 바인딩
    @Bean
    fun eventAlarmQueue(): Queue = Queue(EVENT_QUEUE)

    @Bean
    fun eventAlarmBinding(eventAlarmQueue: Queue, delayedExchange: CustomExchange): Binding {
        return BindingBuilder.bind(eventAlarmQueue).to(delayedExchange).with(EVENT_ROUTING_KEY).noargs()
    }

    companion object {

        const val DELAYED_EXCHANGE = "alarm.exchange"

        // 보스 파티 알람
        const val BOSS_QUEUE = "boss.party.alarm.queue"
        const val BOSS_ROUTING_KEY = "boss.party.alarm.routing.key"

        // 이벤트 종료 알람
        const val EVENT_QUEUE = "event.termination.alarm.queue"
        const val EVENT_ROUTING_KEY = "event.termination.alarm.routing.key"
    }
}