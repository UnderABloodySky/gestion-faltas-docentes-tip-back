package ar.edu.unq.tpi.ciriaqui.service

import io.jsonwebtoken.*
import org.springframework.stereotype.Service
import java.util.*

@Service
class JWTService {
    private val secretKey = "secret"

    fun generateToken(claims: Map<String, Any>): String {
        val expirationMillis = 60 * 60 * 1000
        val now = System.currentTimeMillis()
        val expiration = now + expirationMillis

        return Jwts.builder()
            .setIssuer("kotlin-jwt-demo")
            .setIssuedAt(Date(now))
            .setExpiration(Date(expiration))
            .setSubject("user@example.com")
            .setAudience("https://example.com")
            .setNotBefore(Date(now))
            .setId(UUID.randomUUID().toString())
            .addClaims(claims)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact()
    }

    fun verifyToken(token: String): Boolean {
        return try {
            Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .body
            true
        } catch (e: SignatureException) {
            false
        } catch (e: ExpiredJwtException) {
            false
        } catch (e: MalformedJwtException) {
            false
        } catch (e: Exception) {
            false
        }
    }
}