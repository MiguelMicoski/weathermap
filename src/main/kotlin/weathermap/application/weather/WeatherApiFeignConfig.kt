package weathermap.application.weather

import feign.Request
import feign.codec.ErrorDecoder
import org.springframework.context.annotation.Bean
import weathermap.application.exception.InvalidCityException
import weathermap.application.exception.WeatherProviderRateLimitException
import weathermap.application.exception.WeatherProviderUnauthorizedException
import weathermap.application.exception.WeatherProviderUnavailableException
import java.util.concurrent.TimeUnit

class WeatherApiFeignConfig {

    @Bean
    fun weatherApiErrorDecoder(): ErrorDecoder {
        return ErrorDecoder { _, response ->
            when (response.status()) {
                400, 404 -> InvalidCityException("City not found by weather provider")
                401, 403 -> WeatherProviderUnauthorizedException("Weather provider rejected the configured API key")
                429 -> WeatherProviderRateLimitException("Weather provider rate limit exceeded")
                else -> WeatherProviderUnavailableException("Weather provider returned status ${response.status()}")
            }
        }
    }

    @Bean
    fun weatherApiRequestOptions(): Request.Options {
        return Request.Options(2, TimeUnit.SECONDS, 5, TimeUnit.SECONDS, true)
    }
}
