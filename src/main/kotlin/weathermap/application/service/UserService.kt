package weathermap.application.service

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import weathermap.application.controller.request.CreateUserRequest
import weathermap.application.controller.response.UserResponse
import weathermap.application.model.UserEntity
import weathermap.application.repository.RoleRepository
import weathermap.application.repository.UserRepository
import weathermap.application.security.Roles

@Service
class UserService(
    private val userRepository: UserRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val roleRepository: RoleRepository
) {

    fun save(createUserRequest: CreateUserRequest): UserResponse {

        val password = hashPassword(createUserRequest.password)
        val role = roleRepository.findByName(Roles.USER)
            ?: throw IllegalStateException("Default role ${Roles.USER} not found")

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

}
