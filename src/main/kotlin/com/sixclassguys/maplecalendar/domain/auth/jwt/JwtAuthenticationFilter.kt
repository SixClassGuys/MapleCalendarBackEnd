package com.sixclassguys.maplecalendar.domain.auth.jwt

import com.sixclassguys.maplecalendar.domain.auth.repository.RefreshTokenRepository
import com.sixclassguys.maplecalendar.domain.member.repository.MemberRepository
import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtUtil: JwtUtil,
    private val refreshTokenRepository: RefreshTokenRepository
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val authHeader = request.getHeader("Authorization")
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }

        val accessToken = authHeader.substring(7)

        try {
            // AccessToken 정상
            val claims = jwtUtil.parseClaims(accessToken)
            setAuthentication(claims.subject)
        } catch (e: ExpiredJwtException) {
            // AccessToken 만료 → RefreshToken 확인
            handleRefreshToken(request, response)
        }

        filterChain.doFilter(request, response)
    }

    private fun handleRefreshToken(request: HttpServletRequest, response: HttpServletResponse) {
        val refreshTokenCookie = request.cookies
            ?.firstOrNull { it.name == "refreshToken" }
            ?.value ?: return

        try {
            // 토큰 유효성 확인
            val claims = jwtUtil.parseClaims(refreshTokenCookie)
            if (claims["type"] != "refresh") return

            // DB에서 토큰 조회
            val savedToken = refreshTokenRepository.findByToken(refreshTokenCookie) ?: return
            val member = savedToken.member

            // 새로운 AccessToken 발급
            val newAccessToken = jwtUtil.createAccessToken(member.email)
            response.setHeader("Authorization", "Bearer $newAccessToken")

            setAuthentication(member.email)
        } catch (_: Exception) {}
    }

    private fun setAuthentication(username: String) {
        val auth = UsernamePasswordAuthenticationToken(username, null, emptyList())
        SecurityContextHolder.getContext().authentication = auth
    }
}
