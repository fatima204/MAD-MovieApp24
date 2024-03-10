package com.example.movieappmad24

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.ui.theme.MovieAppMAD24Theme
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale.Companion.FillWidth
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.google.ai.client.generativeai.type.content

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppMAD24Theme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(text = "Movie App")
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.primary,
                            )
                        )
                    },
                    bottomBar = {
                        BottomNavigationBar(
                            items = listOf(
                                BottomItem(
                                    title = "Home",
                                    route = "home",
                                    icon = Icons.Default.Home
                                ),
                                BottomItem(
                                    title = "Favorite",
                                    route = "favorite",
                                    icon = Icons.Default.Star
                                )
                            ),
                            navController = navController,
                            onItemClick = {
                                navController.navigate(it.route)
                            },
                        )
                    }
                ) {
                    value ->
                    Box (
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(value)
                    ){
                        Navigation(navController = navController)
                    }

                }
            }
        }
    }
}


    @Composable
    fun MovieList(movies: List<Movie> = getMovies()) {
        LazyColumn() {
            items(movies) { movie ->
                MovieRow(movie)
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MovieRow(movie: Movie) {
        var showDetails by remember {
            mutableStateOf(false)
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                ),
            shape = ShapeDefaults.Large,
            onClick = {
                showDetails = !showDetails
            },
            elevation = CardDefaults.cardElevation(10.dp)
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = movie.images[0],
                        contentDescription = "Movie Images",
                        contentScale = FillWidth,
                        modifier = Modifier
                            .aspectRatio(ratio = 20f / 8.5f)
                    )
                    /*Image(
                        painter = painterResource(id = R.drawable.movie_image),
                        contentScale = ContentScale.Crop,
                        contentDescription = "placeholder image"
                    )*/
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        Icon(
                            tint = MaterialTheme.colorScheme.secondary,
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Add to favorites"
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = movie.title)
                    Icon(
                        modifier = Modifier
                            .clickable {
                                showDetails = !showDetails
                            },
                        imageVector =
                        if (showDetails) Icons.Filled.KeyboardArrowDown
                        else Icons.Default.KeyboardArrowUp, contentDescription = "show more"
                    )
                }
                if (showDetails == true) {

                    DetailsText(movie = movie)

                }
            }
        }
    }

    @Composable
    fun FavoriteScreen() {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Favorite screen")
        }
    }

    @Composable
    fun Navigation(navController: NavHostController) {
        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                HomeScreen()
            }
            composable("favorite") {
                FavoriteScreen()
            }
        }
    }

    @Composable
    fun HomeScreen() {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Call your existing MovieList composable to display movies
            MovieList(movies = getMovies())
        }
    }

    @Composable
    fun BottomNavigationBar(
        items: List<BottomItem>,
        navController: NavController,
        modifier: Modifier = Modifier,
        onItemClick: (BottomItem) -> Unit
    ) {
        val backStackEntry = navController.currentBackStackEntryAsState()
        BottomNavigation(
            modifier = modifier,
            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
            elevation = 5.dp
        ) {
            items.forEach { item ->
                val selected = item.route == backStackEntry.value?.destination?.route
                BottomNavigationItem(
                    selected = selected,
                    onClick = { onItemClick(item) },
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.primary,
                    icon = {
                        Column(horizontalAlignment = CenterHorizontally) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title
                            )
                            Text(
                                text = item.title,
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp
                            )
                        }
                    }
                )
            }
        }
    }

    @Composable
    fun DetailsText(movie: Movie) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = "Director: ${movie.director}",
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Released: ${movie.year}",
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Genre: ${movie.genre}",
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Actors: ${movie.actors}",
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Rating: ${movie.rating}",
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Divider(thickness = 1.dp, color = Color.LightGray)
            Text(
                text = "Plot: ${movie.plot}",
                maxLines = 8,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }

    /*
    @Preview
    @Composable
    fun DefaultPreview() {
        MovieAppMAD24Theme {
            MovieList(movies = getMovies())
        }
    }
     */
