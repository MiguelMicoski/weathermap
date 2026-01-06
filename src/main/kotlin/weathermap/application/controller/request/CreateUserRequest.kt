package weathermap.application.controller.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CreateUserRequest(
    @field:NotBlank
    val name: String,
    @field:NotBlank
    @field:Email
    val email: String,
    @field:NotBlank
    val username: String,
    @field:NotBlank
    val password: String,
    @field:NotNull
    val roleIds: List<Long>

)
