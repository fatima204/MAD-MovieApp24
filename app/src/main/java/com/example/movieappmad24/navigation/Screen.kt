package com.example.movieappmad24.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.movieappmad24.screens.BottomItem

sealed class Screen (val route: String){
    object HomeScreen: Screen("home_screen")

    object WatchListScreen: Screen("watchlist_screen")
    object DetailScreen : Screen("detail_screen/{movieId}") {
        fun CreateRoute(movieId: String): String {
            return "detail_screen/$movieId"
        }
    }

}