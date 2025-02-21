package role.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import role.model.Item
import role.repository.ItemRepository

@RestController
@RequestMapping("/api/itens")
class ItemController(
    private val itemRepository: ItemRepository
) {
    
    @GetMapping
    @PreAuthorize("hasAnyRole('Admin','Modifier','Watcher')")
    fun listarTodos(): List<Item> = itemRepository.findAll()
    
    @PostMapping
    @PreAuthorize("hasAnyRole('Admin','Modifier')")
    fun criar(@RequestBody item: Item): Item = itemRepository.save(item)
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('Admin','Modifier')")
    fun atualizar(
        @PathVariable id: Long,
        @RequestBody item: Item
    ): Item {
        require(itemRepository.existsById(id)) { "Item não encontrado" }
        return itemRepository.save(item)
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('Admin','Modifier')")
    fun deletar(@PathVariable id: Long) {
        itemRepository.deleteById(id)
    }
}