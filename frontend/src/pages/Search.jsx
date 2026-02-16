import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../api/axios.js';

export default function Search() {
    const [query, setQuery] = useState('');
    const [results, setResults] = useState([]);
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    const handleSearch = async (e) => {
        e.preventDefault();
        if (!query.trim()) return;

        setLoading(true);
        try {
            const res = await api.get(`/movies/search?query=${encodeURIComponent(query)}`);
            setResults(res.data);
        } catch (err) {
            console.error('Search failed:', err);
            setResults([]);
        } finally {
            setLoading(false);
        }
    };

    const handleMovieClick = (tmdbId) => {
        // Сначала получим локальный movieId через backend
        api.get(`/movies/tmdb/${tmdbId}`)
            .then(res => {
                navigate(`/movie/${res.data.id}`);
            })
            .catch(err => console.error('Failed to fetch movie:', err));
    };

    return (
        <div style={{ padding: '20px', maxWidth: '800px', margin: '0 auto' }}>
            <h1>Find a Movie</h1>

            <form onSubmit={handleSearch} style={{ marginBottom: '20px' }}>
                <input
                    data-testid="search-input"
                    type="text"
                    value={query}
                    onChange={(e) => setQuery(e.target.value)}
                    placeholder="Enter movie title..."
                    style={{ width: '70%', padding: '8px', marginRight: '10px' }}
                />
                <button
                    data-testid="search-button"
                    type="submit"
                    disabled={loading}
                    style={{ padding: '8px 16px' }}
                >
                    {loading ? 'Searching...' : 'Search'}
                </button>
            </form>

            <div data-testid="search-results">
                {results.map(movie => (
                    <div
                        key={movie.id}
                        data-testid={`movie-card-${movie.id}`}
                        onClick={() => handleMovieClick(movie.id)}
                        style={{
                            border: '1px solid #ccc',
                            borderRadius: '4px',
                            padding: '12px',
                            margin: '8px 0',
                            cursor: 'pointer'
                        }}
                    >
                        <h3>{movie.title} ({movie.releaseDate?.split('-')[0] || 'N/A'})</h3>
                        <p>{movie.overview?.substring(0, 150)}...</p>
                    </div>
                ))}
            </div>
        </div>
    );
}
