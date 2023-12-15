package com.vp.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vp.core.db.dao.FavoriteMovieDao
import com.vp.core.db.model.FavoriteMovieEntity

@Database(
    entities = [
        FavoriteMovieEntity::class,
    ],
    version = MovieDbProperties.DB_VERSION,
    exportSchema = true,
)
abstract class MovieDb : RoomDatabase() {
    abstract val favoriteMovieDao: FavoriteMovieDao
}

object MovieDbProperties {
    const val DB_NAME = "movie_db"
    const val DB_VERSION = 1
}