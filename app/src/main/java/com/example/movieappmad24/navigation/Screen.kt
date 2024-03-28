package com.example.movieappmad24.navigation

import androidx.annotation.StringRes
import com.example.movieappmad24.R


sealed class Screen (val route: String){
    object HomeScreen: Screen("home_screen")

    object WatchListScreen: Screen("watchlist_screen")
    object DetailScreen : Screen("detail_screen/{movieId}") {
        fun CreateRoute(movieId: String): String {
            return "detail_screen/$movieId"
        }
    }

}
