package com.androidapp.movieappmvp.model.entity.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.androidapp.movieappmvp.model.entity.dao.DaoMovies
import com.androidapp.movieappmvp.model.entity.dao.DaoMoviesDetail
import com.androidapp.movieappmvp.model.entity.dao.DaoMoviesLike
import com.androidapp.movieappmvp.model.entity.room.data.RoomDetailMovie
import com.androidapp.movieappmvp.model.entity.room.data.RoomLikeMovie
import com.androidapp.movieappmvp.model.entity.room.data.RoomMovie

@Database(
    entities = [RoomDetailMovie::class,
        RoomLikeMovie::class,
        RoomMovie::class], version = 1
)
abstract class DBMovies : RoomDatabase() {

    abstract fun movies(): DaoMovies
    abstract fun moviesLike(): DaoMoviesLike
    abstract fun moviesDetail(): DaoMoviesDetail
}