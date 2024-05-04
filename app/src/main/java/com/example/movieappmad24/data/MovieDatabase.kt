package com.example.movieappmad24.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieImage
import com.example.movieappmad24.worker.WorkManager

@Database(
    entities = [Movie::class, MovieImage::class],  // tables in the db
    version = 3,                // schema version; whenever you change schema you have to increase the version number
    exportSchema = false        // for schema version history updates
)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao // Dao instance so that the DB knows about the Dao
    // add more daos here if you have multiple tables

    // declare as singleton - companion objects are like static variables in Java
    companion object{
        @Volatile   // never cache the value of instance
        private var instance: MovieDatabase? = null

        private fun seed(context: Context): Callback {
            return object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    var workManager = WorkManager(context)
                    workManager.tellWorker()
                }
            }
        }

        fun getDatabase(context: Context): MovieDatabase{
            return instance ?: synchronized(this) { // wrap in synchronized block to prevent race conditions
                Room.databaseBuilder(context, MovieDatabase::class.java, "movie_db")
                    .fallbackToDestructiveMigration() // if schema changes wipe the whole db - there are better migration strategies for production usage
                    .addCallback(seed(context))
                    .build() // create an instance of the db
                    .also {
                        instance = it   // override the instance with newly created db
                    }
            }
        }
    }
}