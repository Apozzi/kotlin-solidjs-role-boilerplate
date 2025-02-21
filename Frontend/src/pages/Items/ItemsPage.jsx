import { createSignal, createResource } from 'solid-js';
import { fetchItems, createItem, deleteItem } from '../../api';

export default function ItemsPage() {
  const [newDescription, setNewDescription] = createSignal('');
  const [newValor, setNewValor] = createSignal('');
  const [items, { refetch }] = createResource(fetchItems);

  const handleCreate = async () => {
    try {
      await createItem({ descricao: newDescription(), valor: parseFloat(newValor()) });
      setNewDescription('');
      setNewValor('');
      refetch();
    } catch (error) {
      console.error(error);
    }
  };

  const handleDelete = async (id) => {
    try {
      await deleteItem(id);
      refetch();
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div class="p-4 max-w-4xl mx-auto">
      <h1 class="text-2xl font-bold mb-4">Gerenciamento de Itens</h1>
      <div class="mb-4 flex space-x-2">
        <input
          type="text"
          placeholder="Descrição"
          class="border rounded p-2 flex-1"
          value={newDescription()}
          onInput={(e) => setNewDescription(e.target.value)}
        />
        <input
          type="number"
          placeholder="Valor"
          class="border rounded p-2 w-32"
          value={newValor()}
          onInput={(e) => setNewValor(e.target.value)}
        />
        <button class="bg-blue-500 text-white rounded p-2" onClick={handleCreate}>
          Criar
        </button>
      </div>
      <ul>
        {items.loading && <li>Carregando...</li>}
        {items() &&
          items().map((item) => (
            <li key={item.id} class="border p-2 rounded mb-2 flex justify-between items-center">
              <div>
                <strong>{item.descricao}</strong> - R$ {item.valor.toFixed(2)}
              </div>
              <div>
                <button class="bg-red-500 text-white rounded p-1" onClick={() => handleDelete(item.id)}>
                  Deletar
                </button>
              </div>
            </li>
          ))}
      </ul>
    </div>
  );
}
