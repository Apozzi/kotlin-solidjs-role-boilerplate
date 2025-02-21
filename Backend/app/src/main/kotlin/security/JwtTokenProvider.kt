package role.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.Claims
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import role.model.User
import java.util.Date

@Component
class JwtTokenProvider {
    private val jwtSecret = "809sdfsd84893dsvsdvds25578vdsdsv9234738298089dszfsdf42380942etter435345389032dfgfdg4sdfs890342809"
    private val jwtExpirationInMs = 3600000 // 1 hora

    fun generateToken(username: String, authorities: Collection<GrantedAuthority>): String {
        val claims: MutableMap<String, Any> = HashMap()
        claims["roles"] = authorities.map { it.authority }

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(username)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + jwtExpirationInMs))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact()
    }

    fun getUsernameFromJWT(token: String): String {
        val claims = getClaimsFromToken(token)
        println(claims)
        return claims.subject ?: throw IllegalArgumentException("Token inválido: Subject ausente")
    }

    fun getRolesFromToken(token: String): List<String> {
        val claims = getClaimsFromToken(token)
        val roles = claims["roles"] as List<*>
        return roles.filterIsInstance<String>()
    }

    fun validateToken(token: String): Boolean {
        try {
            getClaimsFromToken(token)
            return true
        } catch (ex: Exception) {
            return false
        }
    }

    private fun getClaimsFromToken(token: String): Claims {
        return try {
            Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .body
        } catch (e: Exception) {
            throw IllegalArgumentException("Token inválido ou expirado", e)
        }
    }
}