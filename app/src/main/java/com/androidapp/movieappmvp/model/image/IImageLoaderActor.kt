package com.androidapp.movieappmvp.model.image

interface IImageLoaderActor<T> {
    fun loadInto(url: String, container: T)
}