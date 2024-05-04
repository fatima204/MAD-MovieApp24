package com.example.movieappmad24.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieWithImages
import kotlinx.coroutines.launch

interface MovieViewModel {
    fun toggleFavoriteMovie(movie: MovieWithImages)
}