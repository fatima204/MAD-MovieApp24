package com.example.movieappmad24.worker

import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import android.content.Context
import android.util.Log
import com.example.movieappmad24.data.MovieDao
import com.example.movieappmad24.data.MovieDatabase.Companion.getDatabase
import com.example.movieappmad24.models.MovieImage
import com.example.movieappmad24.models.getMovies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class Worker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    private var movieDao = getDatabase(context = context).movieDao()

    override suspend fun doWork(): Result {
        return coroutineScope {
            return@coroutineScope withContext(Dispatchers.Main) {
                return@withContext try {
                    movieDao.addAllMovies(movies = getMovies())
                    val dbId = movieDao.getDbId()
                    val images = mutableListOf<MovieImage>()
                    for(i in 0 until getMovies().size){
                        for(url in getMovies()[i].images){
                            images.add(MovieImage(movieId = dbId[i],url = url))
                        }
                    }
                    movieDao.addAllMovieImages(images)
                    Result.success()
                } catch (throwable: Throwable) {
                    Log.e("error", "ooohhh, an error occured",throwable)
                    Result.failure()
                }
            }
        }
    }
}