package ar.edu.unq.tpi.ciriaqui

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["ar.edu.unq.tpi.ciriaqui.security", "ar.edu.unq.tpi.ciriaqui"])
class CiriaquiApplication

fun main(args: Array<String>) {
	runApplication<CiriaquiApplication>(*args)
}
