package com.androidapp.movieappmvp.model.cache

import com.androidapp.movieappmvp.model.api.data.Cast
import com.androidapp.movieappmvp.model.api.data.MoviesDetail
import com.androidapp.movieappmvp.model.entity.room.data.RoomDetailMovie
import io.reactivex.Single

interface IMoviesDetailCache {

    fun getMoviesDetailForID(id: Long): Single<RoomDetailMovie>
    fun putMoviesDetail(movies: MoviesDetail)

    fun getMovieActors(id: Long): Single<List<Cast>>
    fun putMoviesActors(id: Long, listCast: List<Cast>)
}