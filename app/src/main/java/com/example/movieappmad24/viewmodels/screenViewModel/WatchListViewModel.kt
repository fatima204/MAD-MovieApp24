package com.example.movieappmad24.viewmodels.screenViewModel

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappmad24.data.MovieRepository
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieWithImages
import com.example.movieappmad24.viewmodels.MovieViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WatchListViewModel(private val repository: MovieRepository) : ViewModel(), MovieViewModel {

    private val _favoriteMovieList = MutableStateFlow<List<MovieWithImages>>(emptyList())
    var favoriteMovies: StateFlow<List<MovieWithImages>> = _favoriteMovieList.asStateFlow()

    init{
        getFavoriteMovies()
    }

    private fun getFavoriteMovies() {
        viewModelScope.launch {
            repository.getFavoriteMovies().collect{
                _favoriteMovieList.value = it
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