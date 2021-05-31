package com.androidapp.movieappmvp.di.movie

import com.androidapp.movieappmvp.di.movie.module.MovieModule
import com.androidapp.movieappmvp.di.movieDetail.MovieDetailSubComponent
import com.androidapp.movieappmvp.presenter.PresenterMovieListPresenterDetail
import com.androidapp.movieappmvp.ui.adapter.AdapterMoviesFilm
import dagger.Subcomponent

@MovieScope
@Subcomponent(modules = [MovieModule::class])
interface MovieSubComponent {
    fun movieDetailSubComponent(): MovieDetailSubComponent

    fun inject(presenterMovieList: PresenterMovieListPresenterDetail)
    fun inject(adapterMoviesFilm: AdapterMoviesFilm)
}