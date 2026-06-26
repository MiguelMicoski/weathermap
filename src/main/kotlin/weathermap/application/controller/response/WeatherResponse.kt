package weathermap.application.controller.response

data class WeatherResponse(
    val city: String,
    val region: String?,
    val country: String,
    val localTime: String?,
    val temperatureCelsius: Double,
    val feelsLikeCelsius: Double,
    val humidity: Int,
    val windKph: Double,
    val condition: String,
    val provider: String
)
