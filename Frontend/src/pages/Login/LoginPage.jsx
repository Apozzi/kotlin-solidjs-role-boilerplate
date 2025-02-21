import { createSignal } from 'solid-js';
import { useNavigate } from '@solidjs/router';
import { login } from '../../api';
import './LoginPage.css';

export default function LoginPage() {
  const [email, setEmail] = createSignal('');
  const [password, setPassword] = createSignal('');
  const [error, setError] = createSignal('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    try {
      const token = await login(email(), password());
      localStorage.setItem('authToken', token);
      navigate('/', { replace: true });
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <div class="login-page">
      <form onSubmit={handleSubmit} class="login-form">
        <h2>Login</h2>
        {error() && <p class="error">{error()}</p>}
        <div class="form-group">
          <label>Email:</label>
          <input
            type="email"
            value={email()}
            onInput={(e) => setEmail(e.target.value)}
            required
          />
        </div>
        <div class="form-group">
          <label>Senha:</label>
          <input
            type="password"
            value={password()}
            onInput={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <button type="submit">Entrar</button>
      </form>
    </div>
  );
}