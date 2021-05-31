package com.androidapp.movieappmvp.model.image

interface IImageLoaderMovie<T> {
    fun loadInto(url: String, container: T)
}