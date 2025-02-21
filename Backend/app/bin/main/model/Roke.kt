package role.model

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "roles")
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    
    @Column(unique = true)
    val name: String,
    
    @Column(name = "deleted_at")
    val deletedAt: Instant? = null
)