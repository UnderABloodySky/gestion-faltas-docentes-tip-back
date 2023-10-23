package ar.edu.unq.tpi.ciriaqui.controller

import ar.edu.unq.tpi.ciriaqui.service.JWTService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.Collections.emptyMap

@RestController
@RequestMapping("/jwt")
class JWTController(
    private @Autowired val jwtService: JWTService) {
    @GetMapping("/generate")
    fun generateToken(): ResponseEntity<String> {
        val token = jwtService.generateToken(emptyMap())
        return ResponseEntity.ok(token)
    }
    @GetMapping("/verify")
    fun verifyToken(@RequestParam("token") token: String): Boolean {
        return jwtService.verifyToken(token)
    }
}