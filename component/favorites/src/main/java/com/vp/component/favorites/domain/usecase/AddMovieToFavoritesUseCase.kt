package com.vp.component.favorites.domain.usecase

import com.vp.component.favorites.data.repository.FavoriteRepository
import javax.inject.Inject

class AddMovieToFavoritesUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    data class Params(val id: String, val title: String)
    suspend fun invoke(params: Params) {
        return favoriteRepository.addMovie(id = params.id, title = params.title)
    }
}