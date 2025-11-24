package weathermap.application.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import weathermap.application.model.UserEntity

@Service
class TokenService (
    @Value("\${jwt.secret}")
    val secret: String
) {


    fun generateToken(userEntity: UserEntity): String {
        try {
            val algorithm = Algorithm.HMAC256(secret)
            val token = JWT.create()
                .withIssuer("auth0")
                .withSubject(userEntity.login)
                .withExpiresAt(java.util.Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 2))
        } catch ()
    }
}