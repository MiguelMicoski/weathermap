package weathermap.application.weather

import feign.Request
import feign.Response
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Test
import weathermap.application.exception.InvalidCityException
import weathermap.application.exception.WeatherProviderRateLimitException
import weathermap.application.exception.WeatherProviderUnauthorizedException
import weathermap.application.exception.WeatherProviderUnavailableException
import java.nio.charset.StandardCharsets
import java.util.Collections

class WeatherApiFeignConfigTest {

    private val errorDecoder = WeatherApiFeignConfig().weatherApiErrorDecoder()

    @Test
    fun `should map invalid city status`() {
        val exception = errorDecoder.decode("WeatherApiClient#getCurrentWeather", responseWithStatus(400))

        assertInstanceOf(InvalidCityException::class.java, exception)
    }

    @Test
    fun `should map unauthorized provider status`() {
        val exception = errorDecoder.decode("WeatherApiClient#getCurrentWeather", responseWithStatus(401))

        assertInstanceOf(WeatherProviderUnauthorizedException::class.java, exception)
    }

    @Test
    fun `should map provider rate limit status`() {
        val exception = errorDecoder.decode("WeatherApiClient#getCurrentWeather", responseWithStatus(429))

        assertInstanceOf(WeatherProviderRateLimitException::class.java, exception)
    }

    @Test
    fun `should map provider unavailable status`() {
        val exception = errorDecoder.decode("WeatherApiClient#getCurrentWeather", responseWithStatus(500))

        assertInstanceOf(WeatherProviderUnavailableException::class.java, exception)
    }

    private fun responseWithStatus(status: Int): Response {
        val request = Request.create(
            Request.HttpMethod.GET,
            "/current.json",
            Collections.emptyMap(),
            null,
            StandardCharsets.UTF_8,
            null
        )

        return Response.builder()
            .status(status)
            .reason("test")
            .request(request)
            .build()
    }
}
