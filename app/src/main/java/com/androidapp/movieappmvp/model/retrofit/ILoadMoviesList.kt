package com.androidapp.movieappmvp.model.retrofit

import com.androidapp.movieappmvp.model.EnumTypeMovie
import com.androidapp.movieappmvp.model.api.data.Movie
import io.reactivex.Single

interface ILoadMoviesList {
    fun loadMovieInServer(movieType: EnumTypeMovie): Single<List<Movie>>
}