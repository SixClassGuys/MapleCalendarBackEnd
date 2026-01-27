package com.sixclassguys.maplecalendar.domain.auth.controller

import com.sixclassguys.maplecalendar.domain.auth.dto.AuthGoogleRequest
import com.sixclassguys.maplecalendar.domain.auth.dto.AuthResult
import com.sixclassguys.maplecalendar.domain.auth.dto.TokenRequest
import com.sixclassguys.maplecalendar.domain.auth.dto.LoginResponse
import com.sixclassguys.maplecalendar.domain.auth.dto.TokenResponse
import com.sixclassguys.maplecalendar.domain.auth.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {

//    @GetMapping("/characters")
//    suspend fun getCharacters(
//        @RequestHeader("x-nxopen-api-key") apiKey: String
//    ): ResponseEntity<LoginResponse> {
//        // 서비스의 suspend 함수를 직접 호출
//        val response = authService.loginAndGetCharacters(apiKey)
//
//        return ResponseEntity.ok(response)
//    }

    @PostMapping("/reissue")
    fun reissue(@RequestBody request: TokenRequest): ResponseEntity<TokenResponse> {
        val response = authService.reissue(request)

        return ResponseEntity.ok(response)
    }

    data class AppleLoginRequest(val sub: String, val email: String)

    @PostMapping("/google")
    fun googleLogin(
        @RequestBody request: AuthGoogleRequest
    ): ResponseEntity<LoginResponse> {

        // 로그인 처리 → AccessToken + RefreshToken 반환
        val loginResponse = authService.loginWithGoogle(
            request.idToken,
            request.fcmToken,
            request.platform
        )

        return ResponseEntity.ok(loginResponse)
    }


    @PostMapping("/apple")
    fun appleLogin(@RequestBody request: AppleLoginRequest): ResponseEntity<AuthResult> {
        val member = authService.loginWithApple(request.sub, request.email)
        return ResponseEntity.ok(member)
    }
}