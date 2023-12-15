package com.vp.component.favorites.domain.usecase

import com.vp.component.favorites.data.repository.FavoriteRepository
import com.vp.component.favorites.domain.model.FavoriteMovieDomain
import com.vp.component.favorites.domain.model.mapToDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RemoveMovieFromFavoritesUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    data class Params(val id: String)
    suspend fun invoke(params: Params) {
        return favoriteRepository.removeMovie(id = params.id)
    }
}