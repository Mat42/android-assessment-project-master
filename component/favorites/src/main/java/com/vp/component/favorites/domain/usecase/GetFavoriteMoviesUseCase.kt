package com.vp.component.favorites.domain.usecase

import android.util.Log
import com.vp.component.favorites.data.repository.FavoriteRepository
import com.vp.component.favorites.domain.model.FavoriteMovieDomain
import com.vp.component.favorites.domain.model.mapToDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFavoriteMoviesUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {

    fun invoke(): Flow<List<FavoriteMovieDomain>> {
        return favoriteRepository.getMovies().map { it.map { it.mapToDomain() } }
    }
}