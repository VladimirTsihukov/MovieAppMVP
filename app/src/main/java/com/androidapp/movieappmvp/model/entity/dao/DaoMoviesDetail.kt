package com.androidapp.movieappmvp.model.entity.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.androidapp.movieappmvp.model.entity.room.data.RoomDetailMovie

@Dao
interface DaoMoviesDetail {

    @Query("SELECT * FROM tableMoviesDetails WHERE id = :id")
    fun getMovieFromID(id: Long): RoomDetailMovie

    @Query("UPDATE tableMoviesDetails SET nameActors = :name WHERE id = :id")
    fun setNameActor(name: String, id: Long)

    @Query("UPDATE tableMoviesDetails SET profilePaths = :profile WHERE id = :id")
    fun setProfilePaths(profile: String, id: Long)

    @Query("SELECT nameActors FROM tableMoviesDetails WHERE id = :id")
    fun getNameActor(id: Long): String

    @Query("SELECT profilePaths FROM tableMoviesDetails WHERE id = :id")
    fun getProfilePaths(id: Long): String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieDetail(movieDetail: RoomDetailMovie)
}