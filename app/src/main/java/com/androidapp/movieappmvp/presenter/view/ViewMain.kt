package com.androidapp.movieappmvp.presenter.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface ViewMain: MvpView {
    fun setVisibilityProgressBar(checkInternet: Boolean)
}