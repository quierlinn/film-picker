package com.quierlinn.filmpicker.service;

import com.quierlinn.filmpicker.dto.MovieListRequest;
import com.quierlinn.filmpicker.dto.MovieListResponse;
import com.quierlinn.filmpicker.entity.Movie;
import com.quierlinn.filmpicker.entity.MovieList;
import com.quierlinn.filmpicker.entity.MovieListItem;
import com.quierlinn.filmpicker.repository.MovieListRepository;
import com.quierlinn.filmpicker.repository.MovieListItemRepository;
import com.quierlinn.filmpicker.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieListService {

    private final MovieListRepository listRepository;
    private final MovieListItemRepository listItemRepository;
    private final MovieRepository movieRepository;
    private final UserService userService;

    public MovieList createList(MovieListRequest request) {
        var user = userService.getCurrentUser();
        MovieList list = new MovieList();
        list.setName(request.getName());
        list.setUser(user);
        return listRepository.save(list);
    }

    public List<MovieList> getUserLists() {
        var user = userService.getCurrentUser();
        return listRepository.findByUserId(user.getId());
    }

    public void addMovieToList(Long listId, Long movieId) {
        var user = userService.getCurrentUser();
        MovieList list = listRepository.findById(listId)
                .orElseThrow(() -> new RuntimeException("List not found"));

        // Проверка: принадлежит ли подборка пользователю
        if (!list.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        if (!listItemRepository.existsByListIdAndMovieId(listId, movieId)) {
            MovieListItem item = new MovieListItem();
            item.setList(list);
            item.setMovie(movie);
            listItemRepository.save(item);
        }
    }

    public MovieListResponse getListWithMovies(Long listId) {
        var user = userService.getCurrentUser();
        MovieList list = listRepository.findById(listId)
                .orElseThrow(() -> new RuntimeException("List not found"));

        if (!list.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }

        var movies = listItemRepository.findByListId(listId).stream()
                .map(MovieListItem::getMovie)
                .collect(Collectors.toList());

        MovieListResponse response = new MovieListResponse();
        response.setId(list.getId());
        response.setName(list.getName());
        response.setMovies(movies);
        return response;
    }
}
