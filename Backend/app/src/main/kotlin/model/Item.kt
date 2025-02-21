package role.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant

@Entity
@Table(name = "itens")
data class Item(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    
    val descricao: String,
    
    val valor: Double,
    
    @CreationTimestamp
    @Column(name = "criado_em")
    val criadoEm: Instant? = null,
    
    @UpdateTimestamp
    @Column(name = "atualizado_em")
    val atualizadoEm: Instant? = null
)