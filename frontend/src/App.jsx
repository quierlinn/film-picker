// src/App.jsx
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider, useAuth } from './context/AuthContext.jsx';
import Login from './pages/Login.jsx';
import Search from './pages/Search.jsx';
import MovieDetail from './pages/MovieDetail.jsx'; // ← добавь этот импорт

const ProtectedRoute = ({ children }) => {
    const { user, loading } = useAuth();
    if (loading) return <div>Loading...</div>;
    if (!user) return <Navigate to="/login" />;
    return children;
};

function AppContent() {
    return (
        <Routes>
            <Route path="/login" element={<Login />} />
            <Route
                path="/search"
                element={
                    <ProtectedRoute>
                        <Search />
                    </ProtectedRoute>
                }
            />
            <Route
                path="/movie/:id"
                element={
                    <ProtectedRoute>
                        <MovieDetail />
                    </ProtectedRoute>
                }
            />
            <Route path="*" element={<Navigate to="/login" />} />
        </Routes>
    );
}

export default function App() {
    return (
        <AuthProvider>
            <BrowserRouter>
                <AppContent />
            </BrowserRouter>
        </AuthProvider>
    );
}
