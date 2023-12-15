package com.vp.component.favorites.data.repository

import com.vp.core.db.dao.FavoriteMovieDao
import com.vp.core.db.model.FavoriteMovieEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FavoriteRepository {
    suspend fun addMovie(id: String, title: String)
    suspend fun removeMovie(id: String)
    fun getMovies(): Flow<List<FavoriteMovieEntity>>

    suspend fun isFavorite(id: String): Boolean
}

class FavoriteRepositoryImpl @Inject constructor(
    private val dao: FavoriteMovieDao
): FavoriteRepository {

    override suspend fun addMovie(id: String, title: String) {
        dao.insert(FavoriteMovieEntity(id, title))
    }

    override suspend fun removeMovie(id: String) {
        dao.deleteFavorite(id)
    }

    override fun getMovies(): Flow<List<FavoriteMovieEntity>> {
        return dao.getFavorites()
    }

    override suspend fun isFavorite(id: String): Boolean {
        return dao.isFavorite(id) != 0
    }
}