package com.androidapp.movieappmvp.model.entity.room.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.androidapp.movieappmvp.model.entity.room.DatabaseContact
import kotlinx.android.parcel.Parcelize

@Entity(tableName = DatabaseContact.TABLE_NAME_MOVIES_DETAIL)
@Parcelize
data class RoomDetailMovie(
    @PrimaryKey
    val id: Long,

    val title: String,
    val overview: String,
    val backdrop: String,
    val ratings: Double,
    val numberOfRatings: Int,
    val minimumAge: Int,
    val runtime: Long = -1,
    val genres: String = "",
    val nameActors: String = "",
    val profilePaths: String ="",
) : Parcelable
