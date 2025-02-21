import NavBar from './components/NavBar';
import { useNavigate } from '@solidjs/router';
import { onMount } from 'solid-js';

export default function Layout(props) {
  const navigate = useNavigate();

  onMount(() => {
    const token = localStorage.getItem('authToken');
    if (!token) {
      navigate('/login', { replace: true });
    }
  });

  return (
    <div class="flex min-h-screen">
      <NavBar />
      <main class="flex-1 p-6 bg-gray-100">
        {props.children}
      </main>
    </div>
  );
}