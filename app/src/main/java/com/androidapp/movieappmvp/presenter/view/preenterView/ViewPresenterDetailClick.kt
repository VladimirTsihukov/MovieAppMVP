package com.androidapp.movieappmvp.presenter.view.preenterView

import com.androidapp.movieappmvp.model.api.data.Movie

interface ViewPresenterDetailClick {
    fun clickMovie(movieId: Long)
    fun clickLikeIcon(iconLike: Boolean, movies: Movie)
}