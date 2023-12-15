package com.vp.component.favorites.di

import com.vp.component.favorites.data.repository.FavoriteRepository
import com.vp.component.favorites.data.repository.FavoriteRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class FavoriteRepositoryModule {
    @Binds
    abstract fun providesFavoriteRepository(repository: FavoriteRepositoryImpl): FavoriteRepository
}