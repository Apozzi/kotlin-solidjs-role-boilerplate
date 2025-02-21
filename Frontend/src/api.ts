export const API_BASE_URL = "http://localhost:8080";

type HttpMethod = 'GET' | 'POST' | 'PUT' | 'DELETE';

/**
 * Função genérica para realizar requisições HTTP
 * @param method Método HTTP (GET, POST, PUT, DELETE)
 * @param endpoint Endpoint da API (ex: '/api/itens')
 * @param body Corpo da requisição (opcional)
 * @param includeAuth Indica se deve incluir o header de autenticação (padrão: true)
 */
async function request<T>(method: HttpMethod, endpoint: string, body?: any, includeAuth: boolean = true): Promise<T> {
  const headers: HeadersInit = includeAuth ? { ...getAuthHeaders() } : {};
  if (body) {
    headers['Content-Type'] = 'application/json';
  }

  const config: RequestInit = {
    method,
    headers,
    body: body ? JSON.stringify(body) : undefined
  };

  const response = await fetch(`${API_BASE_URL}${endpoint}`, config);
  if (!response.ok) {
    throw new Error(`Erro ${response.status}: ${response.statusText}`);
  }
  const contentType = response.headers.get('Content-Type');
  return contentType?.includes('application/json') ? response.json() : response.text();
}

const getAuthHeaders = (): Record<string, string> => {
  const token = localStorage.getItem("authToken");
  return token ? { "Authorization": `Bearer ${token}` } : {};
};

// ------------------------
// CRUD para Itens
// ------------------------
export const fetchItems = () => request<ItemType[]>('GET', '/api/itens');
export const createItem = (item: ItemPayload) => request<ItemType>('POST', '/api/itens', item);
export const updateItem = (id: number, item: ItemPayload) => request<ItemType>('PUT', `/api/itens/${id}`, item);
export const deleteItem = (id: number) => request<void>('DELETE', `/api/itens/${id}`);

// ------------------------
// CRUD para Roles
// ------------------------
export const fetchRoles = () => request<RoleType[]>('GET', '/api/roles');
export const createRole = (role: RolePayload) => request<RoleType>('POST', '/api/roles', role);
export const updateRole = (id: number, role: RolePayload) => request<RoleType>('PUT', `/api/roles/${id}`, role);
export const deleteRole = (id: number) => request<void>('DELETE', `/api/roles/${id}`);

// ------------------------
// CRUD para Usuários
// ------------------------
export const fetchUsers = () => request<UserType[]>('GET', '/api/users');
export const createUser = (user: UserPayload) => request<UserType>('POST', '/api/users', user);
export const updateUser = (id: number, user: UserPayload) => request<UserType>('PUT', `/api/users/${id}`, user);
export const deleteUser = (id: number) => request<void>('DELETE', `/api/users/${id}`);

// ------------------------
// Autenticação
// ------------------------
export const login = (email: string, password: string) => request<{ token: string }>(
'POST',
  '/login',
  { email, password },
  false
).then(data => data.token);

type ItemType = { id: number; descricao: string; valor: number };
type ItemPayload = { descricao: string; valor: number };
type RoleType = { id: number; name: string };
type RolePayload = { name: string };
type UserType = { id: number; name: string; email: string };
type UserPayload = { name: string; email: string; password: string };