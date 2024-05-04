package com.example.movieappmad24.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieImage(
    @PrimaryKey(autoGenerate = true)
    val dbId: Long = 0,
    val movieId: Long,
    val url: String
)