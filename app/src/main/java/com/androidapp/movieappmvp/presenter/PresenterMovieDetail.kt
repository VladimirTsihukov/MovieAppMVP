package com.androidapp.movieappmvp.presenter

import android.util.Log
import com.androidapp.movieappmvp.model.ClassKey
import com.androidapp.movieappmvp.model.retrofit.ILoadMoviesDetail
import com.androidapp.movieappmvp.presenter.view.ViewMovieDetail
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class PresenterMovieDetail(val movieId: Long) : MvpPresenter<ViewMovieDetail>() {

    @Inject
    lateinit var retrofitLoadDetail: ILoadMoviesDetail

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var mainThreadScheduler: Scheduler

    private val disposable: CompositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadMovieDetail()
        loadMovieActors()
    }

    private fun loadMovieDetail() {
        disposable.add(retrofitLoadDetail.retrofitLoadMoviesDetail(movieId)
            .observeOn(mainThreadScheduler)
            .subscribe({ movieDetail ->
                viewState.initViewMovieDetail(movieDetail)
                viewState.invisibleLoader()
            }, { error ->
                Log.e(ClassKey.LOG_KEY, "Error in loadMovieDetail(): ${error.message}")
            }))
    }

    private fun loadMovieActors() {
        disposable.add(retrofitLoadDetail.retrofitLoadMovieActors(movieId)
            .observeOn(mainThreadScheduler)
            .subscribe({ cast ->
                viewState.updateAdapterActor(cast)
                viewState.invisibleLoader()
            }, { error ->
                Log.e(ClassKey.LOG_KEY, "Error in loadMovieActors(): ${error.message}")
            }))
    }

    fun backPressed(): Boolean {
        router.exit()
        disposable.dispose()
        return true
    }
}