package com.vp.core.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.vp.core.db.model.FAVORITE_MOVIE_TABLE
import com.vp.core.db.model.FavoriteMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao : BaseDao<FavoriteMovieEntity> {

    @Query("SELECT * FROM $FAVORITE_MOVIE_TABLE")
    fun getFavorites(): Flow<List<FavoriteMovieEntity>>

    @Query("DELETE FROM $FAVORITE_MOVIE_TABLE WHERE id = :id")
    suspend fun deleteFavorite(id: String)

    @Query("SELECT COUNT(*) FROM $FAVORITE_MOVIE_TABLE WHERE id = :id")
    suspend fun isFavorite(id: String): Int
}