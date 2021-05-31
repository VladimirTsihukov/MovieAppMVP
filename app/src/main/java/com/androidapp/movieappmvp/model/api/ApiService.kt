package com.androidapp.movieappmvp.model.api

import com.androidapp.movieappmvp.model.ClassKey.QUERY_PARAM_MOVIE_ID_COR
import com.androidapp.movieappmvp.model.api.data.MovieActors
import com.androidapp.movieappmvp.model.api.data.MoviesDetail
import com.androidapp.movieappmvp.model.api.data.MoviesList
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("popular")
    fun getMoviePopular(): Single<MoviesList>

    @GET("top_rated")
    fun getMoviesTopRate(): Single<MoviesList>

    @GET("{movie_id}")
    fun getMovieDetails(
        @Path(QUERY_PARAM_MOVIE_ID_COR) movieID: Long
    ): Single<MoviesDetail>

    @GET("{movie_id}/credits")
    fun getMovieActors(
        @Path(QUERY_PARAM_MOVIE_ID_COR) movieID: Long
    ): Single<MovieActors>
}