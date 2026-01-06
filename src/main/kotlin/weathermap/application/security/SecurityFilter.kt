package weathermap.application.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import weathermap.application.repository.UserRepository
import weathermap.application.service.TokenService

@Component
class SecurityFilter(
    private val tokenService: TokenService,
    private val userRepository: UserRepository
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = recoveryToken(request)
        if (token != null) {
            var subject = tokenService.validateToken(token)
            val user = userRepository.findByLogin(subject)

            val authentication = UsernamePasswordAuthenticationToken(user, null, user?.authorities)
            SecurityContextHolder.getContext().authentication = authentication
        }
        filterChain.doFilter(request, response)
    }

    fun recoveryToken(request: HttpServletRequest): String? {
        val token = request.getHeader("Authorization") ?: return null
        return token.replace("Bearer ", "")
    }
}