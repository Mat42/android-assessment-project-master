package com.vp.core.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

const val FAVORITE_MOVIE_TABLE = "favorite_movies"

@Entity(tableName = FAVORITE_MOVIE_TABLE)
data class FavoriteMovieEntity(
    @PrimaryKey
    val id: String,
    val title: String
)
