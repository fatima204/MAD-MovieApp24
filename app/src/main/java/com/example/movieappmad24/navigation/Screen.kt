package com.example.movieappmad24.navigation


sealed class Screen (val route: String){
    object HomeScreen : Screen(route = "home_screen")
    object WatchListScreen : Screen(route = "watchlist_screen")
    object DetailScreen : Screen(route = "detail_screen/{movieId}") {
        fun CreateRoute(movieId: String): String {
            return "detail_screen/$movieId"
        }
    }

}
