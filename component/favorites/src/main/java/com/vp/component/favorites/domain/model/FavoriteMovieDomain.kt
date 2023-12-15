package com.vp.component.favorites.domain.model

import com.vp.core.db.model.FavoriteMovieEntity

data class FavoriteMovieDomain(
    val id: String,
    val title: String
)

fun FavoriteMovieEntity.mapToDomain(): FavoriteMovieDomain {
    return FavoriteMovieDomain(
        id = id,
        title = title
    )
}
