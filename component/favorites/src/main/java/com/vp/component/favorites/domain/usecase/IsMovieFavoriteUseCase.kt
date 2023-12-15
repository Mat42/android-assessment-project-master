package com.vp.component.favorites.domain.usecase

import com.vp.component.favorites.data.repository.FavoriteRepository
import javax.inject.Inject

class IsMovieFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    data class Params(val id: String)
    suspend fun invoke(params: Params): Boolean {
        return favoriteRepository.isFavorite(id = params.id)
    }
}