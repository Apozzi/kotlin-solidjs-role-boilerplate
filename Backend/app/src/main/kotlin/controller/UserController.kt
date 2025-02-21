package role.controller

import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import role.model.User
import role.repository.UserRepository
import java.time.LocalDate

@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasRole('Admin')")
class UserController(
    private val userRepository: UserRepository
) {

    @GetMapping
    fun getAllUsers(): List<User> = userRepository.findAll()

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): User =
        userRepository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado")
        }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(@RequestBody user: User): User {
        val newUser = user.copy(
            registerDate = LocalDate.now(),
            createdAt = java.time.Instant.now()
        )
        return userRepository.save(newUser)
    }

    @PutMapping("/{id}")
    fun updateUser(
        @PathVariable id: Long,
        @RequestBody userUpdate: User
    ): User {
        val existingUser = userRepository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado")
        }

        return userRepository.save(
            existingUser.copy(
                name = userUpdate.name,
                email = userUpdate.email,
                password = userUpdate.password,
                updatedAt = java.time.Instant.now()
            )
        )
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUser(@PathVariable id: Long) {
        if (!userRepository.existsById(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado")
        }
        userRepository.deleteById(id)
    }

    // Endpoint para soft delete
    @PatchMapping("/{id}/soft-delete")
    fun softDeleteUser(@PathVariable id: Long): User {
        val user = userRepository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado")
        }
        return userRepository.save(user.copy(deletedAt = java.time.Instant.now()))
    }
}