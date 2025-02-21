package role.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import role.model.Role
import role.repository.RoleRepository

@RestController
@RequestMapping("/api/roles")
class RoleController(
    private val roleRepository: RoleRepository
) {

    @GetMapping
    fun getAllRoles(): List<Role> = roleRepository.findAll()

    @GetMapping("/{id}")
    fun getRoleById(@PathVariable id: Long): Role =
        roleRepository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Role não encontrada")
        }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createRole(@RequestBody role: Role): Role =
        roleRepository.save(role)

    @PutMapping("/{id}")
    fun updateRole(
        @PathVariable id: Long,
        @RequestBody roleUpdate: Role
    ): Role {
        val existingRole = roleRepository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Role não encontrada")
        }

        return roleRepository.save(
            existingRole.copy(
                name = roleUpdate.name,
                deletedAt = roleUpdate.deletedAt
            )
        )
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteRole(@PathVariable id: Long) {
        if (!roleRepository.existsById(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Role não encontrada")
        }
        roleRepository.deleteById(id)
    }
}