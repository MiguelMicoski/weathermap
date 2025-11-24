package weathermap.application.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import weathermap.application.controller.request.CreateUserRequest
import weathermap.application.controller.response.UserResponse
import weathermap.application.service.UserService

@RestController
@RequestMapping("/v1/users")
class UserController(private val userService: UserService) {

    @PostMapping
    fun save (@RequestBody createUserRequest: CreateUserRequest): ResponseEntity<UserResponse> {
        val response = userService.save(createUserRequest)
        return ResponseEntity.ok(response)
    }
}