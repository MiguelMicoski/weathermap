# Weathermap

Weathermap is a Kotlin/Spring Boot API for user authentication and current weather lookup by city.

The application currently provides:

- JWT-based authentication.
- Public user registration with a default user role.
- Flyway database migrations for users, roles, and user-role relationships.
- A protected weather endpoint backed by WeatherAPI.com.

## Requirements

- Java 17
- MySQL
- WeatherAPI.com API key

## Configuration

Configuration is read from environment variables. Safe local defaults are defined in `src/main/resources/application.yml`.

Required or commonly customized variables:

```env
SERVER_PORT=7171
DB_URL=jdbc:mysql://localhost/weathermap
DB_USERNAME=root
DB_PASSWORD=admin
JWT_SECRET=change-me-dev-secret
WEATHER_API_BASE_URL=https://api.weatherapi.com/v1
WEATHER_API_KEY=your-weather-api-key
```

Create a local `.env` file for development secrets if needed. The file is ignored by Git.

Spring Boot does not automatically load `.env` files, so export the variables before running the app or configure them in your IDE run configuration.

PowerShell example:

```powershell
$env:WEATHER_API_KEY="your-weather-api-key"
.\gradlew.bat bootRun
```

## Running Tests

```powershell
.\gradlew.bat test
```

## API

Public endpoints:

- `POST /v1/auth/login`
- `POST /v1/users`

Authenticated endpoint:

- `GET /v1/weather?city=Sao Paulo`

The weather endpoint returns a stable internal response and hides the provider-specific response format from API consumers.
