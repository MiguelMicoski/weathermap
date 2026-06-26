package weathermap.application.weather

import com.fasterxml.jackson.annotation.JsonProperty
import weathermap.application.controller.response.WeatherResponse
import weathermap.application.exception.WeatherProviderMalformedResponseException

data class WeatherApiCurrentResponse(
    val location: WeatherApiLocation?,
    val current: WeatherApiCurrent?
) {

    fun toWeatherResponse(): WeatherResponse {
        val providerLocation = location ?: throw malformedResponse()
        val providerCurrent = current ?: throw malformedResponse()

        return WeatherResponse(
            city = providerLocation.name ?: throw malformedResponse(),
            region = providerLocation.region,
            country = providerLocation.country ?: throw malformedResponse(),
            localTime = providerLocation.localTime,
            temperatureCelsius = providerCurrent.temperatureCelsius ?: throw malformedResponse(),
            feelsLikeCelsius = providerCurrent.feelsLikeCelsius ?: throw malformedResponse(),
            humidity = providerCurrent.humidity ?: throw malformedResponse(),
            windKph = providerCurrent.windKph ?: throw malformedResponse(),
            condition = providerCurrent.condition?.text ?: throw malformedResponse(),
            provider = "WeatherAPI.com"
        )
    }

    private fun malformedResponse(): WeatherProviderMalformedResponseException {
        return WeatherProviderMalformedResponseException("Weather provider returned an incomplete response")
    }
}

data class WeatherApiLocation(
    val name: String?,
    val region: String?,
    val country: String?,
    @JsonProperty("localtime")
    val localTime: String?
)

data class WeatherApiCurrent(
    @JsonProperty("temp_c")
    val temperatureCelsius: Double?,
    @JsonProperty("feelslike_c")
    val feelsLikeCelsius: Double?,
    val humidity: Int?,
    @JsonProperty("wind_kph")
    val windKph: Double?,
    val condition: WeatherApiCondition?
)

data class WeatherApiCondition(
    val text: String?
)
