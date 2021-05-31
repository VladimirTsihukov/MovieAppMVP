package com.androidapp.movieappmvp.navigator

import com.androidapp.movieappmvp.ui.fragment.FragmentMovieDetail
import com.androidapp.movieappmvp.ui.fragment.FragmentMovieList
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screen {
    class MoviesListScreen : SupportAppScreen() {
        override fun getFragment() = FragmentMovieList.newInstance()
    }

    class MovieDetail(private val id: Long) : SupportAppScreen() {
        override fun getFragment() = FragmentMovieDetail.newInstance(id)
    }
}