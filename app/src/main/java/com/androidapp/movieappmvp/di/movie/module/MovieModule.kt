package com.androidapp.movieappmvp.di.movie.module

import android.widget.ImageView
import com.androidapp.movieappmvp.di.movie.MovieScope
import com.androidapp.movieappmvp.model.api.ApiService
import com.androidapp.movieappmvp.model.cache.IMoviesCache
import com.androidapp.movieappmvp.model.cache.roomcache.CacheRoomMovies
import com.androidapp.movieappmvp.model.entity.room.db.DBMovies
import com.androidapp.movieappmvp.model.image.IImageLoaderMovie
import com.androidapp.movieappmvp.model.newtwork.INetworkStatus
import com.androidapp.movieappmvp.model.retrofit.ILoadMoviesList
import com.androidapp.movieappmvp.model.retrofit.RetrofitLoadMoviesList
import com.androidapp.movieappmvp.ui.image.GlideImageLoaderActorMovies
import dagger.Module
import dagger.Provides

@Module
class MovieModule {

    @MovieScope
    @Provides
    fun imageLoaderMovie(): IImageLoaderMovie<ImageView> = GlideImageLoaderActorMovies()

    @MovieScope
    @Provides
    fun retrofitLoaderMovies(api: ApiService, networkStatus: INetworkStatus, cache: IMoviesCache)
            : ILoadMoviesList = RetrofitLoadMoviesList(api, networkStatus, cache)

    @MovieScope
    @Provides
    fun movieCache(db: DBMovies): IMoviesCache = CacheRoomMovies(db)
}