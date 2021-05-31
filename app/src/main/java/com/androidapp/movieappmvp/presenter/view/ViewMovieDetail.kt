package com.androidapp.movieappmvp.presenter.view

import com.androidapp.movieappmvp.model.api.data.Cast
import com.androidapp.movieappmvp.model.entity.room.data.RoomDetailMovie
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface ViewMovieDetail: MvpView {
    fun initViewMovieDetail(movieDetail: RoomDetailMovie)
    fun updateAdapterActor(listActor: List<Cast>)
    fun invisibleLoader()
    fun release()
}