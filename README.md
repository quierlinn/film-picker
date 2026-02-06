# Film Picker

A movie discovery and discussion platform built for QA/SDET practice.

- Users can register, browse movies (via TMDB), rate them (1–10), create custom lists, and view top-rated films.
- Includes both backend (Spring Boot) and frontend (React) for full-stack testing practice.

## Tech Stack

- **Backend**: Java 17, Spring Boot, Spring Security (JWT), Spring Data JPA, PostgreSQL
- **Frontend**: React, Vite, Axios
- **External API**: [The Movie Database (TMDB)](https://www.themoviedb.org/)
- **Testing**: JUnit 5, Mockito, REST Assured, Testcontainers, Playwright (planned)

## Local Setup

### Backend
1. Create a `.env` file in `backend/`:
   ```env
   TMDB_API_KEY=your_tmdb_api_key
   SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/film_picker
   SPRING_DATASOURCE_USERNAME=postgres
   SPRING_DATASOURCE_PASSWORD=postgres
   JWT_SECRET=verySecretKey123!
