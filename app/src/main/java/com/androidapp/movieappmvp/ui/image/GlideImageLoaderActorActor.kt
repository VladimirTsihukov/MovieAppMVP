package com.androidapp.movieappmvp.ui.image

import android.widget.ImageView
import com.androidapp.movieappmvp.R
import com.androidapp.movieappmvp.model.image.IImageLoaderActor
import com.bumptech.glide.Glide

class GlideImageLoaderActorActor: IImageLoaderActor<ImageView> {
    override fun loadInto(url: String, container: ImageView) {
        Glide.with(container.context)
            .load(url)
            .error(R.drawable.ic_placeholder_actor)
            .into(container)
    }
}