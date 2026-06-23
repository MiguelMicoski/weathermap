package weathermap.application.service

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import weathermap.application.controller.request.CreateUserRequest
import weathermap.application.controller.response.UserResponse
import weathermap.application.model.UserEntity
import weathermap.application.repository.RoleRepository
import weathermap.application.repository.UserRepository

@Service
class UserService(
    private val userRepository: UserRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val roleRepository: RoleRepository
) {

    fun save(createUserRequest: CreateUserRequest): UserResponse {

        val password = hashPassword(createUserRequest.password)
        val role = roleRepository.findByName(DEFAULT_USER_ROLE)
            ?: throw IllegalStateException("Default role $DEFAULT_USER_ROLE not found")

        val userEntity = userRepository.save(
            UserEntity(
                name = createUserRequest.name,
                login = createUserRequest.username,
                email = createUserRequest.email,
                credential = password,
                role = listOf(role)
            )
        )

        return userEntity.toResponse()
    }

    fun hashPassword(rawPassword: String): String {
        return bCryptPasswordEncoder.encode(rawPassword)
    }

    companion object {
        private const val DEFAULT_USER_ROLE = "ROLE_USER"
    }

}
