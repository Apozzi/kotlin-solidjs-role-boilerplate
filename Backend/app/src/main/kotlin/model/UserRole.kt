package role.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.io.Serializable
import java.time.Instant

@Entity
@Table(name = "user_roles")
data class UserRole(
    @EmbeddedId
    val id: UserRoleId,
    
    @Column(name = "deleted_at")
    val deletedAt: Instant? = null,
    
    @CreationTimestamp
    @Column(name = "created_at")
    val createdAt: Instant? = null,
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    val updatedAt: Instant? = null
)

@Embeddable
data class UserRoleId(
    @Column(name = "user_id")
    val userId: Long,
    
    @Column(name = "role_id")
    val roleId: Long
) : Serializable
