package role.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service
import role.repository.UserRepository

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByEmail(username)
            ?: throw UsernameNotFoundException("Usuário não encontrado")

        val authorities = user.roles.map { SimpleGrantedAuthority("ROLE_${it.name}") }

        return org.springframework.security.core.userdetails.User(
            user.email,
            user.password,
            authorities
        )
    }
}