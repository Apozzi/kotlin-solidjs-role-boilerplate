import { A, useLocation } from '@solidjs/router';
import { Home, Box, Users, Shield } from 'lucide-solid';

export default function NavBar() {
  const location = useLocation();

  const navItems = [
    { name: 'Home', href: '/', icon: Home },
    { name: 'Itens', href: '/items', icon: Box },
    { name: 'Papéis', href: '/roles', icon: Shield },
    { name: 'Usuários', href: '/users', icon: Users },
  ];

  return (
    <aside class="w-64 h-screen bg-gradient-to-b from-gray-800 to-gray-900 text-white p-6 shadow-lg">
      <h2 class="text-2xl font-bold mb-8 text-center">Menu</h2>
      <nav>
        <ul class="space-y-4">
          {navItems.map(({ name, href, icon: Icon }) => {
            const isActive = location.pathname === href;
            return (
              <li>
                <A
                  href={href}
                  class={`flex items-center gap-3 px-4 py-2 rounded-lg transition-colors ${
                    isActive
                      ? 'bg-gray-700 text-white'
                      : 'text-gray-300 hover:bg-gray-700 hover:text-white'
                  }`}
                >
                  <Icon size={20} />
                  <span class="text-white">{name}</span>
                </A>
              </li>
            );
          })}
        </ul>
      </nav>
    </aside>
  );
}