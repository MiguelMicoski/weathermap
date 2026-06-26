package weathermap.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class WeathermapApplication

fun main(args: Array<String>) {
	runApplication<WeathermapApplication>(*args)
}
