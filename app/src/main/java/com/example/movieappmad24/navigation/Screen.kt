package com.example.movieappmad24.navigation

sealed class Screen (val route: String){
    object HomeScreen: Screen("home_screen")
    object DetailScreen : Screen("detailscreen/{movieId}") {
        fun CreateRoute(movieId: String): String {
            return "detailscreen/$movieId"
        }
    }

}