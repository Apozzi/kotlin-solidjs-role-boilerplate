import { createSignal, createResource } from 'solid-js';
import { fetchUsers, createUser, deleteUser } from '../../api';

export default function UsersPage() {
  const [newName, setNewName] = createSignal('');
  const [newEmail, setNewEmail] = createSignal('');
  const [newPassword, setNewPassword] = createSignal('');
  const [users, { refetch }] = createResource(fetchUsers);

  const handleCreate = async () => {
    try {
      await createUser({ name: newName(), email: newEmail(), password: newPassword() });
      setNewName('');
      setNewEmail('');
      setNewPassword('');
      refetch();
    } catch (error) {
      console.error(error);
    }
  };

  const handleDelete = async (id) => {
    try {
      await deleteUser(id);
      refetch();
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div class="p-4 max-w-4xl mx-auto">
      <h1 class="text-2xl font-bold mb-4">Gerenciamento de Usuários</h1>
      <div class="mb-4 grid grid-cols-1 gap-2">
        <input
          type="text"
          placeholder="Nome"
          class="border rounded p-2"
          value={newName()}
          onInput={(e) => setNewName(e.target.value)}
        />
        <input
          type="email"
          placeholder="Email"
          class="border rounded p-2"
          value={newEmail()}
          onInput={(e) => setNewEmail(e.target.value)}
        />
        <input
          type="password"
          placeholder="Senha"
          class="border rounded p-2"
          value={newPassword()}
          onInput={(e) => setNewPassword(e.target.value)}
        />
        <button class="bg-blue-500 text-white rounded p-2" onClick={handleCreate}>
          Criar Usuário
        </button>
      </div>
      <ul>
        {users.loading && <li>Carregando...</li>}
        {users() &&
          users().map((user) => (
            <li key={user.id} class="border p-2 rounded mb-2 flex justify-between items-center">
              <div>
                <strong>{user.name}</strong> - {user.email}
              </div>
              <div>
                <button class="bg-red-500 text-white rounded p-1" onClick={() => handleDelete(user.id)}>
                  Deletar
                </button>
              </div>
            </li>
          ))}
      </ul>
    </div>
  );
}