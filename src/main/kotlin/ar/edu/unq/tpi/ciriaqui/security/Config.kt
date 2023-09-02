package ar.edu.unq.tpi.ciriaqui.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class CorsConfig {

    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowedOrigins = listOf("http://localhost:8080", "http://localhost:4200") // Reemplaza con tu dominio permitido
        config.allowedMethods = listOf("GET", "POST", "PUT", "DELETE")
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }
}