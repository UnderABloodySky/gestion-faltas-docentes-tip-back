package ar.edu.unq.tpi.ciriaqui

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@SpringBootApplication
//@ComponentScan(basePackages = ["ar.edu.unq.tpi.ciriaqui.security", "ar.edu.unq.tpi.ciriaqui"])
class CiriaquiApplication

	fun main(args: Array<String>) {
		runApplication<CiriaquiApplication>(*args)
	}

//	@Bean
//	fun corsConfigurer(): WebMvcConfigurer {
//		return object : WebMvcConfigurer {
//			override fun addCorsMappings(registry: CorsRegistry) {
//				registry.addMapping("*").allowedOrigins("*")
//			}
//		}
//	}

