package com.androidapp.movieappmvp.model

import com.androidapp.movieappmvp.model.api.data.Movie

sealed class AppState {
    data class Success (val data: List<Movie>) : AppState()
    data class Error (val error : Throwable) : AppState()
}
