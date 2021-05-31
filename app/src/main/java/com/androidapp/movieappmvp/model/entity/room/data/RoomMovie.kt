package com.androidapp.movieappmvp.model.entity.room.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.androidapp.movieappmvp.model.entity.room.DatabaseContact
import kotlinx.android.parcel.Parcelize

@Entity (tableName = DatabaseContact.TABLE_NAME_MOVIES)
@Parcelize
data class RoomMovie (
    @PrimaryKey
    val id: Long,
    val title: String,
    val overview: String,
    val posterPath: String,
    val backdropPath: String,
    val voteAverage: Double,
    val voteCount: Long,
    val adult: Boolean,
    var likeMovies: Boolean = false,
    val moviePopular: Boolean = true,
) : Parcelable