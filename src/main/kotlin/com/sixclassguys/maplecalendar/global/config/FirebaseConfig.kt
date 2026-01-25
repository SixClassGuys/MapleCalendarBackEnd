package com.sixclassguys.maplecalendar.global.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import java.io.IOException

@Configuration
class FirebaseConfig {

    private val log = LoggerFactory.getLogger(javaClass)

    private lateinit var firebaseApp: FirebaseApp // Bean 등록용

    @PostConstruct
    fun init() {
        try {
            val fileName = "maplecalendar-4add3-firebase-adminsdk-fbsvc-8bfd99e066.json"
            val serviceAccount = when {
                // 먼저 파일 시스템 경로 확인 (Docker 볼륨 마운트된 경우)
                java.io.File("/app/resources/$fileName").exists() -> {
                    log.info("Firebase 키 파일을 파일 시스템에서 로드: /app/resources/$fileName")
                    java.io.FileInputStream("/app/resources/$fileName")
                }
                // 그 다음 ClassPathResource 확인 (JAR 내부)
                else -> {
                    log.info("Firebase 키 파일을 ClassPath에서 로드: $fileName")
                    ClassPathResource(fileName).inputStream
                }
            }

            val options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build()

            // FirebaseApp이 이미 초기화되어 있지 않은 경우에만 초기화 진행
            if (FirebaseApp.getApps().isEmpty()) {
                firebaseApp = FirebaseApp.initializeApp(options)
                log.info("Firebase Admin SDK 초기화 성공")
            }else{
                firebaseApp = FirebaseApp.getInstance()
            }
        } catch (e: IOException) {
            println("Firebase Admin SDK 초기화 실패: ${e.message}")
            e.printStackTrace()
        }
    }

    @Bean
    fun firebaseApp(): FirebaseApp {
        return firebaseApp
    }
}