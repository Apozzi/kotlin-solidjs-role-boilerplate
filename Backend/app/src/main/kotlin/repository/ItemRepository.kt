package role.repository

import org.springframework.data.jpa.repository.JpaRepository
import role.model.Item

interface ItemRepository : JpaRepository<Item, Long>