package com.example.movieappmad24.viewmodels.screenViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappmad24.data.MovieRepository
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieWithImages
import com.example.movieappmad24.viewmodels.MovieViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MovieRepository) : ViewModel(), MovieViewModel {

    private val _movies = MutableStateFlow<List<MovieWithImages>>(emptyList())
    val movies: StateFlow<List<MovieWithImages>> = _movies.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllMovies()
                .distinctUntilChanged()
                .collect { listOfMovies ->
                    _movies.value = listOfMovies
                }
        }
    }

    override fun toggleFavoriteMovie(movie: MovieWithImages) {
        viewModelScope.launch {
            movie.movie.isFavorite != movie.movie.isFavorite

            repository.updateMovie(movie.movie)
        }
    }
}