package com.androidapp.movieappmvp.model.api.data

import android.os.Parcelable
import com.androidapp.movieappmvp.model.entity.room.data.RoomDetailMovie
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MoviesDetail(
    val id: Long,
    val title: String,
    val overview: String,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("vote_average")
    val ratings: Double,
    @SerializedName("vote_count")
    val voteCount: Long,
    val adult: Boolean,
    val runtime: Long,
    val genres: List<Genre>,
): Parcelable

@Parcelize
data class Genre(
    val id: Long,
    val name: String,
): Parcelable

fun MoviesDetail.getMovieRoom(): RoomDetailMovie {
    return RoomDetailMovie(
        id = id,
        title = title,
        overview = overview,
        backdrop = backdropPath,
        ratings = ratings,
        numberOfRatings = voteCount.toInt(),
        minimumAge = if (adult) 16 else 13,
        runtime = runtime,
        genres = genres.joinToString (", "){ it.name },
    )
}