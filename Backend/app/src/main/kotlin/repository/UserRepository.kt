package role.repository

import org.springframework.data.jpa.repository.JpaRepository
import role.model.User

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
}