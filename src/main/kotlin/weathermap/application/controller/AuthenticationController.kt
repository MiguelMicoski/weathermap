package weathermap.application.controller

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import weathermap.application.controller.request.AuthenticationRequest
import weathermap.application.service.AuthenticationService

@RestController
@RequestMapping("/v1/auth")
class AuthenticationController (private val authenticationService: AuthenticationService) {


    @PostMapping("/login")
    fun login (@RequestBody @Valid req: AuthenticationRequest) {
        authenticationService.authenticate(req.username, req.password)

    }
}