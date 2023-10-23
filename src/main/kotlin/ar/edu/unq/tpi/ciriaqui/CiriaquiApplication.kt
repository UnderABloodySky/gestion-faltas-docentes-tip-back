package ar.edu.unq.tpi.ciriaqui

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@SpringBootApplication
@ComponentScan("ar.edu.unq.tpi.ciriaqui", "ar.edu.unq.tpi.ciriaqui.data")
@EntityScan(basePackages = ["ar.edu.unq.tpi.ciriaqui.model"])
class CiriaquiApplication {
    @Bean
    fun cors(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**")
                    .allowedOrigins("http://localhost:4200")
                    .allowedMethods("*")
                    .allowedHeaders("*")
            }
        }
    }
}

fun main(args: Array<String>) {
    runApplication<CiriaquiApplication>(*args)
}

