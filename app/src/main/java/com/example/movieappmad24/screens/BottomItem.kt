package com.example.movieappmad24.screens

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.movieappmad24.navigation.Screen

sealed class BottomItem(val route: String){
    object DetailScreen : BottomItem("detail_screen/{movieId}") {
        fun CreateRoute(movieId: String): String {
            return "detail_screen/$movieId"
        }
    }
}
