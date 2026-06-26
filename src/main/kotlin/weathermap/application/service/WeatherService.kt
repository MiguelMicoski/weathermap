package weathermap.application.service

import feign.FeignException
import feign.RetryableException
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service
import weathermap.application.controller.response.WeatherResponse
import weathermap.application.exception.InvalidCityException
import weathermap.application.exception.WeatherProviderUnauthorizedException
import weathermap.application.exception.WeatherProviderUnavailableException
import weathermap.application.weather.WeatherApiClient
import weathermap.application.weather.WeatherApiProperties

@Service
@EnableConfigurationProperties(WeatherApiProperties::class)
class WeatherService(
    private val weatherApiClient: WeatherApiClient,
    private val weatherApiProperties: WeatherApiProperties
) {

    fun getCurrentWeather(city: String): WeatherResponse {
        val normalizedCity = city.trim()
        if (normalizedCity.isBlank()) {
            throw InvalidCityException("City must not be blank")
        }

        if (weatherApiProperties.key.isBlank()) {
            throw WeatherProviderUnauthorizedException("Weather provider API key is not configured")
        }

        val providerResponse = try {
            weatherApiClient.getCurrentWeather(weatherApiProperties.key, normalizedCity)
        } catch (exception: RetryableException) {
            throw WeatherProviderUnavailableException("Weather provider request timed out", exception)
        } catch (exception: FeignException) {
            throw WeatherProviderUnavailableException("Weather provider request failed", exception)
        }

        return providerResponse.toWeatherResponse()
    }
}
