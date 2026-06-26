package weathermap.application.exception

import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(InvalidCityException::class, ConstraintViolationException::class)
    fun handleInvalidCity(exception: RuntimeException): ResponseEntity<ApiErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiErrorResponse("INVALID_CITY", exception.message ?: "Invalid city"))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(exception: MethodArgumentNotValidException): ResponseEntity<ApiErrorResponse> {
        val message = exception.bindingResult.fieldErrors.firstOrNull()?.defaultMessage ?: "Invalid request"
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiErrorResponse("INVALID_REQUEST", message))
    }

    @ExceptionHandler(WeatherProviderUnauthorizedException::class)
    fun handleProviderUnauthorized(exception: WeatherProviderUnauthorizedException): ResponseEntity<ApiErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.BAD_GATEWAY)
            .body(ApiErrorResponse("WEATHER_PROVIDER_UNAUTHORIZED", exception.message ?: "Weather provider unauthorized"))
    }

    @ExceptionHandler(WeatherProviderRateLimitException::class)
    fun handleProviderRateLimit(exception: WeatherProviderRateLimitException): ResponseEntity<ApiErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.TOO_MANY_REQUESTS)
            .body(ApiErrorResponse("WEATHER_PROVIDER_RATE_LIMIT", exception.message ?: "Weather provider rate limit exceeded"))
    }

    @ExceptionHandler(WeatherProviderUnavailableException::class)
    fun handleProviderUnavailable(exception: WeatherProviderUnavailableException): ResponseEntity<ApiErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(ApiErrorResponse("WEATHER_PROVIDER_UNAVAILABLE", exception.message ?: "Weather provider unavailable"))
    }

    @ExceptionHandler(WeatherProviderMalformedResponseException::class)
    fun handleProviderMalformedResponse(exception: WeatherProviderMalformedResponseException): ResponseEntity<ApiErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.BAD_GATEWAY)
            .body(ApiErrorResponse("WEATHER_PROVIDER_MALFORMED_RESPONSE", exception.message ?: "Malformed weather provider response"))
    }
}
