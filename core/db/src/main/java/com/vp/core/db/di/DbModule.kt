package com.vp.core.db.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.vp.core.db.MovieDb
import com.vp.core.db.MovieDbProperties
import com.vp.core.db.dao.FavoriteMovieDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {
    @Singleton
    @Provides
    fun providesMovieDb(
        app: Application,
    ): MovieDb = Room.databaseBuilder(
        context = app.applicationContext,
        name = MovieDbProperties.DB_NAME,
        klass = MovieDb::class.java,
    ).build()

    @Singleton
    @Provides
    fun providesFavoriteMovieDao(db: MovieDb): FavoriteMovieDao {
        return db.favoriteMovieDao
    }
}