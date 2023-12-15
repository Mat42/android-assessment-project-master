package com.vp.favorites.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vp.component.favorites.domain.usecase.GetFavoriteMoviesUseCase
import com.vp.favorites.model.FavoriteMovie
import com.vp.favorites.model.toFavoriteMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase
): ViewModel() {

    private val _movies: MutableStateFlow<List<FavoriteMovie>> = MutableStateFlow(listOf())
    val movies: Flow<List<FavoriteMovie>> = _movies.asStateFlow()

    init {
        viewModelScope.launch {
            getFavoriteMoviesUseCase.invoke().collectLatest { movies ->
                _movies.value = movies.map { it.toFavoriteMovie() }
            }
        }
    }
}