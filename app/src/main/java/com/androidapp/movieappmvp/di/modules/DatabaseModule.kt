package com.androidapp.movieappmvp.di.modules

import androidx.room.Room
import com.androidapp.movieappmvp.App
import com.androidapp.movieappmvp.model.entity.room.DatabaseContact
import com.androidapp.movieappmvp.model.entity.room.db.DBMovies
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun database(app: App): DBMovies = Room.databaseBuilder(
        app,
        DBMovies::class.java,
        DatabaseContact.DATABASE_NAME_MOVIES
    ).fallbackToDestructiveMigration().build()
}