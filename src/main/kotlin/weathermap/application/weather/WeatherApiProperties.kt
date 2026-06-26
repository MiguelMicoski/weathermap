package weathermap.application.weather

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "weather.api")
data class WeatherApiProperties(
    val baseUrl: String,
    val key: String
)
