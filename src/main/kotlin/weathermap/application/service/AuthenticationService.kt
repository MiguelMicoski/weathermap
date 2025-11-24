package weathermap.application.service

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import weathermap.application.repository.UserRepository

@Service
class AuthenticationService(
    private val userRepository: UserRepository,
    private val authenticationManager: AuthenticationManager
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails? {
        return userRepository.findByLogin(username)
            ?: throw NoSuchElementException("No user found with username: $username")
    }

    fun authenticate(username: String, password: String) {
        val usernamePassword = UsernamePasswordAuthenticationToken(username, password)
        authenticationManager.authenticate(usernamePassword)
    }
}