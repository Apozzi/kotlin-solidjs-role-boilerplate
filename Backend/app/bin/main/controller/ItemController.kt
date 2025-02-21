package role.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/itens")
class ItemController(
    private val itemRepository: ItemRepository
) {
    
    @GetMapping
    fun listarTodos(): List<Item> = itemRepository.findAll()
    
    @PostMapping
    fun criar(@RequestBody item: Item): Item = itemRepository.save(item)
    
    @PutMapping("/{id}")
    fun atualizar(
        @PathVariable id: Long,
        @RequestBody item: Item
    ): Item {
        require(itemRepository.existsById(id)) { "Item não encontrado" }
        return itemRepository.save(item)
    }
    
    @DeleteMapping("/{id}")
    fun deletar(@PathVariable id: Long) {
        itemRepository.deleteById(id)
    }
}