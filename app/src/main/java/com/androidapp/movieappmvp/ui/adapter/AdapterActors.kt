package com.androidapp.movieappmvp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.androidapp.movieappmvp.R
import com.androidapp.movieappmvp.model.ClassKey.BASE_URL_MOVIE_IMAGE
import com.androidapp.movieappmvp.model.api.data.Cast
import com.androidapp.movieappmvp.model.image.IImageLoaderActor
import javax.inject.Inject

class AdapterActors: RecyclerView.Adapter<AdapterActors.HolderActors>() {

    @Inject
    lateinit var imageLoader: IImageLoaderActor<ImageView>

    private var actors = listOf<Cast>()

    fun bindActors(newActor: List<Cast>) {
        actors = newActor
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderActors {
        return HolderActors(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_item_holder_actor, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HolderActors, position: Int) {
        holder.itemView.animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.alpha)
        holder.onBindActor(actors[position])
    }

    override fun getItemCount(): Int = actors.size

    inner class HolderActors(item: View) : RecyclerView.ViewHolder(item) {
        private val imageActor: ImageView = item.findViewById(R.id.img_holder_actor_image)
        private val nameActors: TextView = item.findViewById(R.id.tv_holder_actor_name)

        fun onBindActor(actor: Cast) {
            nameActors.text = actor.name
            imageActor.let {
                imageLoader.loadInto(BASE_URL_MOVIE_IMAGE + actor.profilePath, it)
            }
        }
    }
}
