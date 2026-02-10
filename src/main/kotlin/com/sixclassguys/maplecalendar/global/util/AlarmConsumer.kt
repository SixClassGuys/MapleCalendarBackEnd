package com.sixclassguys.maplecalendar.global.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.sixclassguys.maplecalendar.domain.notification.service.NotificationService
import com.sixclassguys.maplecalendar.global.config.RabbitConfig
import com.sixclassguys.maplecalendar.global.dto.RedisAlarmDto
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

@Service
class AlarmConsumer(
    private val notificationService: NotificationService,
    private val objectMapper: ObjectMapper
) {

    private val log = LoggerFactory.getLogger(javaClass)

    // 1. 보스 파티 알람 전담 마크
    @RabbitListener(queues = [RabbitConfig.BOSS_QUEUE])
    fun consumeBossAlarm(jsonMessage: String) {
        log.info("⚔️ 보스 알람 수신 시작")
        processAlarm(jsonMessage)
    }

    // 2. 이벤트 알람 전담 마크 (새로 추가)
    @RabbitListener(queues = [RabbitConfig.EVENT_QUEUE])
    fun consumeEventAlarm(jsonMessage: String) {
        log.info("🎁 이벤트 알람 수신 시작")
        processAlarm(jsonMessage)
    }

    private fun processAlarm(jsonMessage: String) {
        val alarmDto = try {
            objectMapper.readValue(jsonMessage, RedisAlarmDto::class.java)
        } catch (e: Exception) {
            log.error("❌ 데이터 파싱 실패: $jsonMessage", e)
            return
        }

        try {
            notificationService.processAlarm(alarmDto)
        } catch (e: Exception) {
            log.error("⚠️ 처리 중 오류 발생: ${alarmDto.targetId}", e)
            throw e
        }
    }
}