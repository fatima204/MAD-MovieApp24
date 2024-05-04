package com.example.movieappmad24.viewmodels.screenViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappmad24.data.MovieRepository
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieWithImages
import com.example.movieappmad24.viewmodels.MovieViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: MovieRepository) : ViewModel(), MovieViewModel {

    private val _detailMovie = MutableStateFlow<MovieWithImages?>(null)

    fun getMovieById(id: Long): StateFlow<MovieWithImages?> {
        viewModelScope.launch {
            repository.getById(id).collect{
                _detailMovie.value = it
            }
        }
        return _detailMovie.asStateFlow()
    }

    override fun toggleFavoriteMovie(movie: MovieWithImages) {
        viewModelScope.launch {
            movie.movie.isFavorite != movie.movie.isFavorite
            repository.updateMovie(movie.movie)
        }
    }

}