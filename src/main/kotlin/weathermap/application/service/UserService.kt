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

        val password = bCryptPasswordEncoder.encode(createUserRequest.password)
        val roles = roleRepository.findAllById(createUserRequest.roleIds)

        val userEntity = userRepository.save(
            UserEntity(
                name = createUserRequest.name,
                login = createUserRequest.username,
                email = createUserRequest.email,
                credential = password,
                role = roles
            )
        )

        return userEntity.toResponse()
    }

}