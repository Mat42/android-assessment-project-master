package com.vp.favorites.model

import com.vp.component.favorites.domain.model.FavoriteMovieDomain

data class FavoriteMovie(
    val id: String,
    val title: String
)

fun FavoriteMovieDomain.toFavoriteMovie(): FavoriteMovie {
    return FavoriteMovie(id, title)
}
