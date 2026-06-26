package weathermap.application.exception

class InvalidCityException(message: String) : RuntimeException(message)

class WeatherProviderUnauthorizedException(message: String) : RuntimeException(message)

class WeatherProviderRateLimitException(message: String) : RuntimeException(message)

class WeatherProviderUnavailableException(message: String, cause: Throwable? = null) : RuntimeException(message, cause)

class WeatherProviderMalformedResponseException(message: String) : RuntimeException(message)
