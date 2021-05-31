package com.androidapp.movieappmvp.di.movieDetail

import com.androidapp.movieappmvp.di.movieDetail.module.MovieDetailModule
import com.androidapp.movieappmvp.presenter.PresenterMovieDetail
import com.androidapp.movieappmvp.ui.adapter.AdapterActors
import dagger.Subcomponent

@MovieDetailScope
@Subcomponent(modules = [MovieDetailModule::class])
interface MovieDetailSubComponent {
    fun inject(presenterMovieDetail: PresenterMovieDetail)
    fun inject(adapterActors: AdapterActors)
}