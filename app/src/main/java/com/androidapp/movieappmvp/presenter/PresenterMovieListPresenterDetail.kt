package com.androidapp.movieappmvp.presenter

import android.util.Log
import com.androidapp.movieappmvp.model.AppState
import com.androidapp.movieappmvp.model.ClassKey
import com.androidapp.movieappmvp.model.EnumTypeMovie
import com.androidapp.movieappmvp.model.api.data.Movie
import com.androidapp.movieappmvp.model.cache.IMoviesCache
import com.androidapp.movieappmvp.model.newtwork.INetworkStatus
import com.androidapp.movieappmvp.model.retrofit.ILoadMoviesList
import com.androidapp.movieappmvp.navigator.Screen
import com.androidapp.movieappmvp.presenter.view.ViewMovieList
import com.androidapp.movieappmvp.presenter.view.preenterView.ViewPresenterDetailClick
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class PresenterMovieListPresenterDetail : MvpPresenter<ViewMovieList>(), ViewPresenterDetailClick {

    @Inject
    lateinit var cache: IMoviesCache

    @Inject
    lateinit var retrofitLoadMovies: ILoadMoviesList

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var mainThreadScheduler: Scheduler

    @Inject
    lateinit var networkStatus: INetworkStatus

    private var flagMovieFavorite = false
    private val disposable: CompositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.initAdapter()
        loadMovies(EnumTypeMovie.POPULAR)
    }

    fun loadMovies(typeMovie: EnumTypeMovie) {
        flagMovieFavorite = typeMovie == EnumTypeMovie.FAVORITE
        loadMoviesType(typeMovie)
    }

    private fun loadMoviesType(typeMovie: EnumTypeMovie) {
        if (typeMovie == EnumTypeMovie.FAVORITE) {
            loadMoviesFavorite()
        } else {
            disposable.add(retrofitLoadMovies.loadMovieInServer(typeMovie)
                .observeOn(mainThreadScheduler)
                .subscribe({ movieList ->
                    if (movieList != null) {
                        viewState.getResponse(AppState.Success(movieList))
                    }
                }, {
                    viewState.getResponse(AppState.Error(it))
                    Log.e(ClassKey.LOG_KEY, "Error in loadMoviesType(): ${it.message}")
                })
            )
        }
    }

    private fun loadMoviesFavorite() {
        disposable.add(cache.getCacheMoviesLike().observeOn(mainThreadScheduler)
            .subscribe({
                viewState.getResponse(AppState.Success(it))
            }, {
                viewState.getResponse(AppState.Error(it))
                Log.e(ClassKey.LOG_KEY, "Error in loadMoviesFavorite(): ${it.message}")
            }))
    }

    override fun clickMovie(movieId: Long) {
        router.navigateTo(Screen.MovieDetail(movieId))
    }

    override fun clickLikeIcon(iconLike: Boolean, movies: Movie) {
        if (iconLike) {
            disposable.add(cache.deleteMovieLike(movies.id).observeOn(mainThreadScheduler)
                .subscribe({
                    if (flagMovieFavorite) loadMoviesFavorite()
                }, {
                    viewState.getResponse(AppState.Error(it))
                    Log.v(ClassKey.LOG_KEY, "Error in deleteMovieLike(): ${it.message}")
                }))
        } else {
            putMovieLikeInDB(movies)
        }
    }

    private fun putMovieLikeInDB(movies: Movie) {
        disposable.add(cache.putCacheMoviesLike(movies)
            .observeOn(mainThreadScheduler)
            .subscribe())
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}