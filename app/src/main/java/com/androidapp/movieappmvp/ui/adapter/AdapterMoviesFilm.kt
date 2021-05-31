package com.androidapp.movieappmvp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.androidapp.movieappmvp.R
import com.androidapp.movieappmvp.model.api.data.Movie
import com.androidapp.movieappmvp.model.image.IImageLoaderMovie
import com.androidapp.movieappmvp.presenter.view.preenterView.ViewPresenterDetailClick
import kotlinx.android.synthetic.main.layout_stars_holder.view.*
import kotlinx.android.synthetic.main.view_item_holder_movies.view.*
import javax.inject.Inject
import kotlin.math.roundToInt

class AdapterMoviesFilm(private val presenterPresenterDetail: ViewPresenterDetailClick) :
    RecyclerView.Adapter<AdapterMoviesFilm.HolderMovies>() {

    @Inject
    lateinit var imageLoaderActorMovie: IImageLoaderMovie<ImageView>

    private var movies = listOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderMovies {
        return HolderMovies(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_item_holder_movies, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HolderMovies, position: Int) {
        holder.itemView.animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.alpha)
        holder.onBind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    fun bindMovies(newMovies: List<Movie>) {
        val diffUtilResult = DiffUtil.calculateDiff(DiffUtilMovie(oldList = movies, newList = newMovies))
        diffUtilResult.dispatchUpdatesTo(this)
        movies = newMovies
    }

    inner class HolderMovies(item: View) : RecyclerView.ViewHolder(item) {
        private val imageFilm: ImageView = item.findViewById(R.id.img_holder_film)
        private val iconLike: ImageView = item.findViewById(R.id.img_holder_like)

        init {
            iconLike.setOnClickListener {
                presenterPresenterDetail.clickLikeIcon(movies[adapterPosition].likeMovies, movies[adapterPosition])
                movies[adapterPosition].likeMovies = !movies[adapterPosition].likeMovies
                notifyItemChanged(adapterPosition)
            }
            imageFilm.setOnClickListener {
                presenterPresenterDetail.clickMovie(movies[adapterPosition].id)
            }
        }

        fun onBind(movie: Movie) {
            with(itemView) {
                tv_holder_reviews.text =
                    context.resources.getString(R.string.fragment_reviews).let {
                        String.format(it, "${movie.voteCount}")
                    }
                tv_holder_age_category.visibility = View.VISIBLE
                tv_holder_age_category.text =
                    context.getString(R.string.fragment_age_category).let {
                        String.format(it, "${if (movie.adult) 16 else 13}")
                    }
                tv_holder_film_name.text = movie.title
                img_mask.clipToOutline = true
            }
            imageFilm.clipToOutline = true
            setPosterIcon(movie.posterPath)
            setImageStars((movie.ratings / 2).roundToInt())
            iconLike.setImageResource(
                if (movie.likeMovies) R.drawable.ic_like_off
                else R.drawable.ic_like_on
            )
        }

        private fun setPosterIcon(poster: String) {
            imageLoaderActorMovie.loadInto(poster, imageFilm)
        }

        private fun setImageStars(current: Int) {
            itemView.apply {
                val listStar = listOf<ImageView>(
                    img_holder_star_level_1,
                    img_holder_star_level_2,
                    img_holder_star_level_3,
                    img_holder_star_level_4,
                    img_holder_star_level_5
                )

                listStar.forEachIndexed { index, _ ->
                    if (index < current) {
                        (listStar[index] as? ImageView)?.setImageResource(R.drawable.ic_star_on)
                    } else {
                        (listStar[index] as? ImageView)?.setImageResource(R.drawable.ic_star_off)
                    }
                }
            }
        }
    }
}

