package weathermap.application.weather

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "weatherApiClient",
    url = "\${weather.api.base-url}",
    configuration = [WeatherApiFeignConfig::class]
)
interface WeatherApiClient {

    @GetMapping("/current.json")
    fun getCurrentWeather(
        @RequestParam("key") apiKey: String,
        @RequestParam("q") city: String,
        @RequestParam("aqi") airQuality: String = "no"
    ): WeatherApiCurrentResponse
}
