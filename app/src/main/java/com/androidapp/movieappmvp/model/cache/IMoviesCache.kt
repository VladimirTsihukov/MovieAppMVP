package com.androidapp.movieappmvp.model.cache

import com.androidapp.movieappmvp.model.api.data.Movie
import io.reactivex.Completable
import io.reactivex.Single


interface IMoviesCache {
    fun getCacheMoviesCategory(moviePopular: Boolean): Single<List<Movie>>
    fun putCacheMovies(movies: List<Movie>, moviesPopular: Boolean)
    fun deleteMoviesCategory(moviePopular: Boolean)

    fun getCacheMoviesLike(): Single<List<Movie>>
    fun putCacheMoviesLike(movies: Movie): Completable
    fun getMovieLikeID(): List<Long>
    fun deleteMovieLike(id: Long): Completable
}