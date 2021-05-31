package com.androidapp.movieappmvp.di.movieDetail.module

import android.widget.ImageView
import com.androidapp.movieappmvp.di.movieDetail.MovieDetailScope
import com.androidapp.movieappmvp.model.api.ApiService
import com.androidapp.movieappmvp.model.cache.IMoviesDetailCache
import com.androidapp.movieappmvp.model.cache.roomcache.CacheRoomMovieDetail
import com.androidapp.movieappmvp.model.entity.room.db.DBMovies
import com.androidapp.movieappmvp.model.image.IImageLoaderActor
import com.androidapp.movieappmvp.model.newtwork.INetworkStatus
import com.androidapp.movieappmvp.model.retrofit.ILoadMoviesDetail
import com.androidapp.movieappmvp.model.retrofit.RetrofitLoadMovieDetail
import com.androidapp.movieappmvp.ui.image.GlideImageLoaderActorActor
import dagger.Module
import dagger.Provides

@Module
class MovieDetailModule {

    @MovieDetailScope
    @Provides
    fun imageLoaderActor(): IImageLoaderActor<ImageView> = GlideImageLoaderActorActor()

    @MovieDetailScope
    @Provides
    fun retrofitLoadDetail(api: ApiService, networkStatus: INetworkStatus, cache: IMoviesDetailCache)
            : ILoadMoviesDetail = RetrofitLoadMovieDetail(api, networkStatus, cache)

    @MovieDetailScope
    @Provides
    fun movieDetailCache(db: DBMovies): IMoviesDetailCache = CacheRoomMovieDetail(db)
}