package com.sixclassguys.maplecalendar.global.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {

    @Value("\${redis.host}")
    private lateinit var host: String

    @Value("\${redis.port}")
    private var port: Int = 6379

    @Value("\${redis.password}")
    private lateinit var password: String

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        // 1. 호스트, 포트, 비밀번호 설정
        val redisConfig = RedisStandaloneConfiguration(host, port)
        redisConfig.password = RedisPassword.of(password)

        // 2. 설정된 정보를 바탕으로 커넥션 팩토리 생성
        return LettuceConnectionFactory(redisConfig)
    }

    @Bean
    fun redisTemplate(connectionFactory: RedisConnectionFactory): RedisTemplate<String, Any> {
        val template = RedisTemplate<String, Any>()
        template.connectionFactory = connectionFactory

        // Key는 문자열로
        template.keySerializer = StringRedisSerializer()

        // Value는 JSON으로 (스프링이 내부적으로 안전한 설정을 잡아서 생성해줌)
        template.valueSerializer = RedisSerializer.json()

        return template
    }
}