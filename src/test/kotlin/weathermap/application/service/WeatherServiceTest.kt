package weathermap.application.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import weathermap.application.exception.InvalidCityException
import weathermap.application.exception.WeatherProviderMalformedResponseException
import weathermap.application.exception.WeatherProviderUnauthorizedException
import weathermap.application.weather.WeatherApiClient
import weathermap.application.weather.WeatherApiCondition
import weathermap.application.weather.WeatherApiCurrent
import weathermap.application.weather.WeatherApiCurrentResponse
import weathermap.application.weather.WeatherApiLocation
import weathermap.application.weather.WeatherApiProperties

class WeatherServiceTest {

    @Test
    fun `should return current weather mapped from provider response`() {
        val service = WeatherService(
            weatherApiClient = FakeWeatherApiClient(successfulProviderResponse()),
            weatherApiProperties = WeatherApiProperties(
                baseUrl = "https://api.weatherapi.com/v1",
                key = "test-key"
            )
        )

        val response = service.getCurrentWeather(" Sao Paulo ")

        assertEquals("Sao Paulo", response.city)
        assertEquals("Sao Paulo", response.region)
        assertEquals("Brazil", response.country)
        assertEquals("2026-06-23 21:00", response.localTime)
        assertEquals(18.5, response.temperatureCelsius)
        assertEquals(18.1, response.feelsLikeCelsius)
        assertEquals(72, response.humidity)
        assertEquals(11.2, response.windKph)
        assertEquals("Partly cloudy", response.condition)
        assertEquals("WeatherAPI.com", response.provider)
    }

    @Test
    fun `should reject blank city`() {
        val service = WeatherService(
            weatherApiClient = FakeWeatherApiClient(successfulProviderResponse()),
            weatherApiProperties = WeatherApiProperties(
                baseUrl = "https://api.weatherapi.com/v1",
                key = "test-key"
            )
        )

        assertThrows(InvalidCityException::class.java) {
            service.getCurrentWeather(" ")
        }
    }

    @Test
    fun `should reject missing provider api key`() {
        val service = WeatherService(
            weatherApiClient = FakeWeatherApiClient(successfulProviderResponse()),
            weatherApiProperties = WeatherApiProperties(
                baseUrl = "https://api.weatherapi.com/v1",
                key = ""
            )
        )

        assertThrows(WeatherProviderUnauthorizedException::class.java) {
            service.getCurrentWeather("Sao Paulo")
        }
    }

    @Test
    fun `should reject malformed provider response`() {
        val service = WeatherService(
            weatherApiClient = FakeWeatherApiClient(WeatherApiCurrentResponse(location = null, current = null)),
            weatherApiProperties = WeatherApiProperties(
                baseUrl = "https://api.weatherapi.com/v1",
                key = "test-key"
            )
        )

        assertThrows(WeatherProviderMalformedResponseException::class.java) {
            service.getCurrentWeather("Sao Paulo")
        }
    }

    private fun successfulProviderResponse(): WeatherApiCurrentResponse {
        return WeatherApiCurrentResponse(
            location = WeatherApiLocation(
                name = "Sao Paulo",
                region = "Sao Paulo",
                country = "Brazil",
                localTime = "2026-06-23 21:00"
            ),
            current = WeatherApiCurrent(
                temperatureCelsius = 18.5,
                feelsLikeCelsius = 18.1,
                humidity = 72,
                windKph = 11.2,
                condition = WeatherApiCondition("Partly cloudy")
            )
        )
    }

    private class FakeWeatherApiClient(
        private val response: WeatherApiCurrentResponse
    ) : WeatherApiClient {
        override fun getCurrentWeather(apiKey: String, city: String, airQuality: String): WeatherApiCurrentResponse {
            return response
        }
    }
}
