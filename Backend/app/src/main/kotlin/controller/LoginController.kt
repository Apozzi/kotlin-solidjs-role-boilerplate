package role.controller

import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import role.security.JwtTokenProvider

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val token: String
)

@RestController
class LoginController(
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenProvider: JwtTokenProvider
) {

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): LoginResponse {
        try {
            val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    loginRequest.email,
                    loginRequest.password
                )
            )

            val userDetails = authentication.principal as org.springframework.security.core.userdetails.User
            val token = jwtTokenProvider.generateToken(userDetails.username, userDetails.authorities)
            return LoginResponse(token)
        } catch (e: AuthenticationException) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email ou senha inválidos")
        }
    }
}