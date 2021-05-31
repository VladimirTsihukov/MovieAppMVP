package com.androidapp.movieappmvp.model.entity.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.androidapp.movieappmvp.model.entity.room.data.RoomMovie

@Dao
interface DaoMovies {

    @Query("SELECT * FROM tableMovies WHERE moviePopular = :moviePopular ORDER BY voteAverage DESC")
    fun getMoviesCategory(moviePopular: Boolean): List<RoomMovie>

    @Query ("DELETE FROM tableMovies WHERE moviePopular = :moviePopular")
    fun deleteMoviesCategory(moviePopular: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies (movies: List<RoomMovie>)
}