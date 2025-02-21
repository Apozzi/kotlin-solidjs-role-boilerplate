package role.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    
    val name: String,
    
    @Column(unique = true)
    val email: String,
    
    val password: String,
    
    @Column(name = "register_date")
    val registerDate: LocalDate? = null,
    
    @CreationTimestamp
    @Column(name = "created_at")
    val createdAt: Instant? = null,
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    val updatedAt: Instant? = null,
    
    @Column(name = "deleted_at")
    val deletedAt: Instant? = null
)