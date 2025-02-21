package role.repository

import org.springframework.data.jpa.repository.JpaRepository
import role.model.Role

interface RoleRepository : JpaRepository<Role, Long>