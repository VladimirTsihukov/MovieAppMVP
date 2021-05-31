package com.androidapp.movieappmvp.model.api.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieActors(
    val id: Long,
    val cast: List<Cast>,
): Parcelable

@Parcelize
data class Cast(
    val id: Long = -1,
    val name: String,
    @SerializedName("profile_path")
    val profilePath: String? = null,
): Parcelable