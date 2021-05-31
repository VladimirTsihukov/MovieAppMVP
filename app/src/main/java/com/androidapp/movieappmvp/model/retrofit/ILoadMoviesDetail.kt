package com.androidapp.movieappmvp.model.retrofit

import com.androidapp.movieappmvp.model.api.data.Cast
import com.androidapp.movieappmvp.model.entity.room.data.RoomDetailMovie
import io.reactivex.Single

interface ILoadMoviesDetail {
    fun retrofitLoadMoviesDetail(id: Long): Single<RoomDetailMovie>
    fun retrofitLoadMovieActors(id: Long): Single<List<Cast>>
}