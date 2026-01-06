package weathermap.application.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import weathermap.application.model.UserEntity
import java.util.Date

@Service
class TokenService (
    @Value("\${jwt.secret}")
    val secret: String
) {


    fun generateToken(username: String): String {
        try {
            val algorithm = Algorithm.HMAC256(secret)
            return JWT.create()
                .withIssuer("auth0")
                .withSubject(username)
                .withExpiresAt(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 2))
                .sign(algorithm)
        } catch (jwtCreationException: Exception) {
            throw RuntimeException("Cannot create JWT!", jwtCreationException)
        }
    }

    fun validateToken(token: String?): String {

        try {
            return JWT.require(Algorithm.HMAC256(secret)).withIssuer("auth0").build().verify(token).subject
        } catch (exception: JWTVerificationException) {
            throw RuntimeException("Invalid JWT signature!")
        }
    }
}