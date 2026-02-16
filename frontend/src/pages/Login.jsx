import { useState } from 'react';
import { useAuth } from '../context/AuthContext.jsx';
import { useNavigate } from 'react-router-dom';

export default function Login() {
    const [isLogin, setIsLogin] = useState(true);
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const { login, register } = useAuth();
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        try {
            if (isLogin) {
                await login(email, password);
            } else {
                await register(email, password);
            }
            navigate('/search');
        } catch (err) {
            setError(err.response?.data?.message || 'Authentication failed');
        }
    };

    return (
        <div style={{ maxWidth: '400px', margin: '50px auto', padding: '20px' }}>
            <h2>{isLogin ? 'Login' : 'Register'}</h2>
            {error && <div style={{ color: 'red' }}>{error}</div>}

            <form onSubmit={handleSubmit}>
                <div>
                    <input
                        data-testid="email-input"
                        type="email"
                        placeholder="Email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                        style={{ width: '100%', padding: '8px', margin: '8px 0' }}
                    />
                </div>
                <div>
                    <input
                        data-testid="password-input"
                        type="password"
                        placeholder="Password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                        style={{ width: '100%', padding: '8px', margin: '8px 0' }}
                    />
                </div>
                <button
                    data-testid="submit-button"
                    type="submit"
                    style={{ width: '100%', padding: '10px', backgroundColor: '#007bff', color: 'white', border: 'none' }}
                >
                    {isLogin ? 'Login' : 'Register'}
                </button>
            </form>

            <button
                data-testid="toggle-mode"
                onClick={() => setIsLogin(!isLogin)}
                style={{ marginTop: '16px', background: 'none', border: 'none', color: '#007bff', cursor: 'pointer' }}
            >
                {isLogin ? 'Need an account?' : 'Already have an account?'}
            </button>
        </div>
    );
}
