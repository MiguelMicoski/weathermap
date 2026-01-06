package weathermap.application.controller

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import weathermap.application.controller.request.AuthenticationRequest
import weathermap.application.controller.response.AuthResponse
import weathermap.application.service.AuthenticationService
import weathermap.application.service.TokenService

@RestController
@RequestMapping("/v1/auth")
class AuthenticationController(
    private val authenticationService: AuthenticationService,
    private val tokenService: TokenService) {


    @PostMapping("/login")
    fun login (@RequestBody @Valid req: AuthenticationRequest): ResponseEntity<AuthResponse> {
        val auth = authenticationService.authenticate(req.username, req.password)

        val token = tokenService.generateToken(auth.name ?: "")

        return ResponseEntity.ok(AuthResponse(token))

    }
}