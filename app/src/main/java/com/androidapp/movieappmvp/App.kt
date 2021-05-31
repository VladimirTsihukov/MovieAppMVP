package com.androidapp.movieappmvp

import android.app.Application
import com.androidapp.movieappmvp.di.AppComponent
import com.androidapp.movieappmvp.di.DaggerAppComponent
import com.androidapp.movieappmvp.di.modules.AppModule
import com.androidapp.movieappmvp.di.movie.MovieSubComponent
import com.androidapp.movieappmvp.di.movieDetail.MovieDetailSubComponent

class App : Application() {

    lateinit var appComponent: AppComponent
        private set

    var movieSubComponent: MovieSubComponent? = null
        private set

    var movieDetailSubComponent: MovieDetailSubComponent? = null
        private set

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    fun initMovieSubComponent() = appComponent.movieSubComponent().also {
        movieSubComponent = it
    }

    fun releaseMovieSubComponent() {
        movieSubComponent = null
    }

    fun initMovieDetailSubComponent() = movieSubComponent?.movieDetailSubComponent().also {
        movieDetailSubComponent = it
    }

    fun releaseMovieDetailSubComponent() {
        movieDetailSubComponent = null
    }
}