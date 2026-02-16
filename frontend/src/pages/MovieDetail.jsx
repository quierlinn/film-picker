import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import api from '../api/axios.js';

export default function MovieDetail() {
    const { id } = useParams(); // это movieId из БД
    const [movie, setMovie] = useState(null);
    const [rating, setRating] = useState('');
    const [lists, setLists] = useState([]);
    const [selectedList, setSelectedList] = useState('');
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        fetchMovie();
        fetchLists();
    }, [id]);

    const fetchMovie = async () => {
        try {
            const res = await api.get(`/movies/${id}`);
            setMovie(res.data);
        } catch (err) {
            console.error('Failed to fetch movie:', err);
            alert('Movie not found');
            navigate('/search');
        }
    };

    const fetchLists = async () => {
        try {
            const res = await api.get('/lists');
            setLists(res.data);
        } catch (err) {
            console.error('Failed to fetch lists:', err);
        }
    };

    const handleRate = async (e) => {
        e.preventDefault();
        if (!rating || rating < 1 || rating > 10) {
            alert('Please enter a score between 1 and 10');
            return;
        }

        try {
            await api.post(`/movies/${id}/rate`, { score: parseInt(rating, 10) });
            alert('Rating saved!');
            setRating('');
            fetchMovie(); // обновить данные
        } catch (err) {
            console.error('Rating failed:', err);
            alert('Failed to save rating');
        }
    };

    const handleAddToList = async (e) => {
        e.preventDefault();
        if (!selectedList) return;

        try {
            await api.post(`/lists/${selectedList}/movies`, { movieId: parseInt(id, 10) });
            alert('Added to list!');
            setSelectedList('');
        } catch (err) {
            console.error('Add to list failed:', err);
            alert('Failed to add to list');
        }
    };

    if (!movie) return <div>Loading...</div>;

    return (
        <div style={{ padding: '20px', maxWidth: '800px', margin: '0 auto' }}>
            <h1>{movie.title}</h1>

            {movie.posterPath && (
                <img
                    src={`https://image.tmdb.org/t/p/w300${movie.posterPath}`}
                    alt={movie.title}
                    style={{ width: '200px', height: 'auto', float: 'left', marginRight: '20px' }}
                />
            )}

            <p><strong>Year:</strong> {movie.releaseYear || 'N/A'}</p>
            <p><strong>Genre:</strong> {movie.genre || 'N/A'}</p>
            <p><strong>Description:</strong> {movie.description}</p>

            {/* Rating Form */}
            <div style={{ marginTop: '30px' }}>
                <h3>Rate this movie</h3>
                <form onSubmit={handleRate}>
                    <input
                        data-testid="rating-input"
                        type="number"
                        min="1"
                        max="10"
                        value={rating}
                        onChange={(e) => setRating(e.target.value)}
                        placeholder="1-10"
                        style={{ width: '80px', padding: '6px', marginRight: '10px' }}
                    />
                    <button
                        data-testid="rate-button"
                        type="submit"
                        style={{ padding: '6px 12px' }}
                    >
                        Submit Rating
                    </button>
                </form>
            </div>

            {/* Add to List */}
            {lists.length > 0 && (
                <div style={{ marginTop: '20px' }}>
                    <h3>Add to List</h3>
                    <form onSubmit={handleAddToList}>
                        <select
                            data-testid="list-select"
                            value={selectedList}
                            onChange={(e) => setSelectedList(e.target.value)}
                            style={{ padding: '6px', marginRight: '10px' }}
                        >
                            <option value="">-- Select a list --</option>
                            {lists.map(list => (
                                <option key={list.id} value={list.id}>{list.name}</option>
                            ))}
                        </select>
                        <button
                            data-testid="add-to-list-button"
                            type="submit"
                            disabled={!selectedList}
                            style={{ padding: '6px 12px' }}
                        >
                            Add
                        </button>
                    </form>
                </div>
            )}

            <button
                data-testid="back-to-search"
                onClick={() => navigate('/search')}
                style={{ marginTop: '30px' }}
            >
                ← Back to Search
            </button>
        </div>
    );
}
