package com.vp.detail.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vp.component.favorites.domain.usecase.AddMovieToFavoritesUseCase
import com.vp.component.favorites.domain.usecase.IsMovieFavoriteUseCase
import com.vp.component.favorites.domain.usecase.RemoveMovieFromFavoritesUseCase
import com.vp.detail.DetailActivity
import com.vp.detail.model.MovieDetail
import com.vp.detail.service.DetailService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject
import javax.security.auth.callback.Callback

class DetailsViewModel @Inject constructor(
    private val detailService: DetailService,
    private val addMovieToFavoritesUseCase: AddMovieToFavoritesUseCase,
    private val isMovieFavoriteUseCase: IsMovieFavoriteUseCase,
    private val removeMovieFromFavoritesUseCase: RemoveMovieFromFavoritesUseCase
) : ViewModel() {

    private val details: MutableLiveData<MovieDetail> = MutableLiveData()
    private val title: MutableLiveData<String> = MutableLiveData()
    private val loadingState: MutableLiveData<LoadingState> = MutableLiveData()
    private var isFavorite: Boolean = false

    init {
        getFavoriteStatus()
    }

    fun title(): LiveData<String> = title

    fun details(): LiveData<MovieDetail> = details

    fun state(): LiveData<LoadingState> = loadingState

    fun fetchDetails() {
        loadingState.value = LoadingState.IN_PROGRESS
        detailService.getMovie(DetailActivity.queryProvider.getMovieId())
            .enqueue(object : Callback, retrofit2.Callback<MovieDetail> {
                override fun onResponse(call: Call<MovieDetail>, response: Response<MovieDetail>) {
                    details.postValue(response.body())

                    response.body()?.title?.let {
                        title.postValue(it)
                    }

                    loadingState.value = LoadingState.LOADED
                }

                override fun onFailure(call: Call<MovieDetail>, t: Throwable) {
                    details.postValue(null)
                    loadingState.value = LoadingState.ERROR
                }
            })
    }

    private fun getFavoriteStatus() {
        viewModelScope.launch {
            isFavorite =
                isMovieFavoriteUseCase.invoke(IsMovieFavoriteUseCase.Params(DetailActivity.queryProvider.getMovieId()))
        }
    }

    fun updateFavoriteStatus() {
        if (isFavorite) {
            removeFromFavorite()
        } else {
            addToFavorite()
        }
        isFavorite = !isFavorite
    }

    private fun addToFavorite() {
        title.value?.let { title ->
            viewModelScope.launch {
                addMovieToFavoritesUseCase.invoke(
                    AddMovieToFavoritesUseCase.Params(
                        id = DetailActivity.queryProvider.getMovieId(),
                        title = title
                    )
                )
            }
        }
    }

    fun removeFromFavorite() {
        viewModelScope.launch {
            removeMovieFromFavoritesUseCase.invoke(
                RemoveMovieFromFavoritesUseCase.Params(
                    DetailActivity.queryProvider.getMovieId()
                )
            )
        }
    }

    enum class LoadingState {
        IN_PROGRESS, LOADED, ERROR
    }
}