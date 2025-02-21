import { createSignal, createResource } from 'solid-js';
import { fetchRoles, createRole, deleteRole } from '../../api';

export default function RolesPage() {
  const [newRoleName, setNewRoleName] = createSignal('');
  const [roles, { refetch }] = createResource(fetchRoles);

  const handleCreate = async () => {
    try {
      await createRole({ name: newRoleName() });
      setNewRoleName('');
      refetch();
    } catch (error) {
      console.error(error);
    }
  };

  const handleDelete = async (id) => {
    try {
      await deleteRole(id);
      refetch();
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div class="p-4 max-w-4xl mx-auto">
      <h1 class="text-2xl font-bold mb-4">Gerenciamento de Papéis</h1>
      <div class="mb-4 flex space-x-2">
        <input
          type="text"
          placeholder="Nome do Papel"
          class="border rounded p-2 flex-1"
          value={newRoleName()}
          onInput={(e) => setNewRoleName(e.target.value)}
        />
        <button class="bg-blue-500 text-white rounded p-2" onClick={handleCreate}>
          Criar
        </button>
      </div>
      <ul>
        {roles.loading && <li>Carregando...</li>}
        {roles() &&
          roles().map((role) => (
            <li key={role.id} class="border p-2 rounded mb-2 flex justify-between items-center">
              <div>
                <strong>{role.name}</strong>
              </div>
              <div>
                <button class="bg-red-500 text-white rounded p-1" onClick={() => handleDelete(role.id)}>
                  Deletar
                </button>
              </div>
            </li>
          ))}
      </ul>
    </div>
  );
}