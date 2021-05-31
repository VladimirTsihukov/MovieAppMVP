package com.androidapp.movieappmvp.ui.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidapp.movieappmvp.App
import com.androidapp.movieappmvp.R
import com.androidapp.movieappmvp.di.movie.MovieSubComponent
import com.androidapp.movieappmvp.model.AppState
import com.androidapp.movieappmvp.model.EnumTypeMovie
import com.androidapp.movieappmvp.presenter.PresenterMovieListPresenterDetail
import com.androidapp.movieappmvp.presenter.view.ViewMovieList
import com.androidapp.movieappmvp.ui.BackButtonListener
import com.androidapp.movieappmvp.ui.adapter.AdapterMoviesFilm
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class FragmentMovieList : MvpAppCompatFragment(R.layout.fragment_movies_list), BackButtonListener,
    ViewMovieList {

    private lateinit var adapter: AdapterMoviesFilm
    private lateinit var recycler: RecyclerView
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var mShimmerContainer: ShimmerFrameLayout

    private var movieSubComponent: MovieSubComponent? = null

    private val presenter by moxyPresenter {
        movieSubComponent = App.instance.initMovieSubComponent()
        PresenterMovieListPresenterDetail().apply {
            movieSubComponent?.inject(this)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieSubComponent = App.instance.initMovieSubComponent()

        initShimmerContainer(view)
        initBottomNavigation()
        initRecycler(view)
    }

    private fun initShimmerContainer(view: View) {
        mShimmerContainer = view.findViewById(R.id.shimmer_view_container)
        startAnimateShimmer()
    }

    private fun initBottomNavigation() {
        activity?.let {
            bottomNav = it.findViewById(R.id.bottom_navigation)
            bottomNav.setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.nav_popular -> {
                        presenter.loadMovies(EnumTypeMovie.POPULAR)
                        true
                    }
                    R.id.nav_top -> {
                        presenter.loadMovies(EnumTypeMovie.TOP)
                        true
                    }
                    R.id.nav_favorite -> {
                        presenter.loadMovies(EnumTypeMovie.FAVORITE)
                        true
                    }
                    else -> {
                        true
                    }
                }
            }
        }
        bottomNav.visibility = View.VISIBLE
    }

    private fun initRecycler(view: View) {
        recycler = view.findViewById(R.id.res_view_move_list)
        recycler.apply {
            val spanCount =  when (resources.configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> 4
                else -> 2
            }
            layoutManager = GridLayoutManager(activity, spanCount)
        }
    }

    override fun initAdapter() {
        adapter = AdapterMoviesFilm(presenter).apply {
            movieSubComponent?.inject(this)
        }
        recycler.adapter = adapter
    }

    override fun getResponse(success: AppState) {
        stopAnimateShimmer()
        when (success) {
            is AppState.Success -> adapter.bindMovies(success.data)
            is AppState.Error -> showError(success.error)
        }
    }

    private fun showError(error: Throwable) {
        Toast.makeText(activity, error.message, Toast.LENGTH_SHORT).show()
    }

    override fun release() {
        App.instance.releaseMovieSubComponent()
        movieSubComponent = null
    }

    private fun startAnimateShimmer() {
        mShimmerContainer.visibility = View.VISIBLE
        mShimmerContainer.startShimmerAnimation()
    }

    private fun stopAnimateShimmer() {
        mShimmerContainer.stopShimmerAnimation()
        mShimmerContainer.visibility = View.GONE
    }

    override fun backPressed() = presenter.backPressed()

    companion object {
        fun newInstance() = FragmentMovieList()
    }
}