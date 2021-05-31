package com.androidapp.movieappmvp.presenter.view

import com.androidapp.movieappmvp.model.AppState
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface ViewMovieList: MvpView {
    fun initAdapter()
    fun getResponse(success: AppState)
    fun release()

}