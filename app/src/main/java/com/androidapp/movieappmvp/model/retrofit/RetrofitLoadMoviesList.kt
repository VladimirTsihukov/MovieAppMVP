package com.androidapp.movieappmvp.model.retrofit

import com.androidapp.movieappmvp.model.EnumTypeMovie
import com.androidapp.movieappmvp.model.api.ApiService
import com.androidapp.movieappmvp.model.api.data.Movie
import com.androidapp.movieappmvp.model.api.data.MoviesList
import com.androidapp.movieappmvp.model.cache.IMoviesCache
import com.androidapp.movieappmvp.model.newtwork.INetworkStatus
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class RetrofitLoadMoviesList(
    private val api: ApiService,
    private val networkStatus: INetworkStatus,
    private val cache: IMoviesCache
) : ILoadMoviesList {

    override fun loadMovieInServer(movieType: EnumTypeMovie): Single<List<Movie>> =
        networkStatus.isOnlineSingle().flatMap { online ->
            val typeMovie = movieType.name == EnumTypeMovie.POPULAR.name
            if (!online) return@flatMap cache.getCacheMoviesCategory(false)
            if (typeMovie) {
                retrofitLoadMovies(movieType, api.getMoviePopular())
            } else {
                retrofitLoadMovies(movieType, api.getMoviesTopRate())
            }
        }.subscribeOn(Schedulers.io())

    private fun retrofitLoadMovies(
        movieType: EnumTypeMovie,
        observer: Single<MoviesList>
    ): Single<List<Movie>> =
        observer.map { movieList ->
            deleteMovieTypeInDb(movieType)
            val listIdMovieLike = cache.getMovieLikeID()
            movieList.results.forEach {
                if (listIdMovieLike.contains(it.id)) {
                    it.likeMovies = true
                }
            }
            cache.putCacheMovies(movieList.results, false)
            movieList.results
        }

    private fun deleteMovieTypeInDb(movieType: EnumTypeMovie) {
        val flagMovieType = movieType.name == EnumTypeMovie.POPULAR.name
        cache.deleteMoviesCategory(flagMovieType)
    }
}