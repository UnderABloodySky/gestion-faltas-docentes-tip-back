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
        println("ejecuta corsFilter Bean")
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowedOrigins = listOf("*") // Reemplaza con tu dominio permitido
        config.allowedMethods = listOf("*")
//        config.allowedHeaders = listOf("*")
//        config.allowedOriginPatterns = listOf("*")
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }
}