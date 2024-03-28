package com.example.movieappmad24.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchListScreen(navController: NavController) {

    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Your Watchlist") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                NavigationBarItem(
                    label = { Text("Home") },
                    selected = navBackStackEntry?.destination?.route == Screen.HomeScreen.route,
                    onClick = { navController.navigate(Screen.HomeScreen.route) },
                    icon = { Icon(
                        imageVector = Icons.Filled.Home,
                        contentDescription = "Go to home"
                    )}
                )
                NavigationBarItem(
                    label = { Text("Watchlist") },
                    selected = navBackStackEntry?.destination?.route == Screen.WatchListScreen.route,
                    onClick = { navController.navigate(Screen.WatchListScreen.route)  },
                    icon = { Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Go to watchlist"
                    )}
                )
            }
        }
    ){ innerPadding ->
        MovieList(
            modifier = Modifier.padding(innerPadding),
            movies = getMovies(),
            navController = navController
        )
    }
}