package com.vp.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vp.component.favorites.domain.usecase.AddMovieToFavoritesUseCase
import com.vp.component.favorites.domain.usecase.IsMovieFavoriteUseCase
import com.vp.component.favorites.domain.usecase.RemoveMovieFromFavoritesUseCase
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

    private lateinit var movieId: String

    fun init(movieId: String) {
        this.movieId = movieId
        getFavoriteStatus()
        fetchDetails()
    }

    fun title(): LiveData<String> = title

    fun details(): LiveData<MovieDetail> = details

    fun state(): LiveData<LoadingState> = loadingState

    private fun fetchDetails() {
        loadingState.value = LoadingState.IN_PROGRESS
        detailService.getMovie(movieId)
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
                isMovieFavoriteUseCase.invoke(IsMovieFavoriteUseCase.Params(movieId))
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
                        id = movieId,
                        title = title
                    )
                )
            }
        }
    }

    private fun removeFromFavorite() {
        viewModelScope.launch {
            removeMovieFromFavoritesUseCase.invoke(
                RemoveMovieFromFavoritesUseCase.Params(
                    movieId
                )
            )
        }
    }

    enum class LoadingState {
        IN_PROGRESS, LOADED, ERROR
    }
}