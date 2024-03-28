package com.example.movieappmad24.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieappmad24.screens.DetailScreen
import com.example.movieappmad24.screens.HomeScreen
import com.example.movieappmad24.screens.WatchListScreen

@Composable
fun Navigation() {

    val navController = rememberNavController() // create a NavController instance

    NavHost(navController = navController, // pass the NavController to NavHost
        startDestination = Screen.HomeScreen.route) {  // pass a start destination

        composable(route = Screen.HomeScreen.route){   // route with name "homescreen" navigates to HomeScreen composable
            HomeScreen(navController = navController)
        }

        composable(route = Screen.WatchListScreen.route){   // route with name "homescreen" navigates to HomeScreen composable
            WatchListScreen(navController = navController)
        }

        composable(
            route = Screen.DetailScreen.route,
            arguments = listOf(navArgument(name = "movieId") {type = NavType.StringType})
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("movieId")?.let { DetailScreen(movieId = it, navController) }
        }
    }
}