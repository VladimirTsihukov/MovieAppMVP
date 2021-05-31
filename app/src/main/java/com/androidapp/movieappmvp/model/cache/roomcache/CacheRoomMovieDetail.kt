package com.androidapp.movieappmvp.model.cache.roomcache

import com.androidapp.movieappmvp.model.ClassKey.SEPARATOR
import com.androidapp.movieappmvp.model.api.data.Cast
import com.androidapp.movieappmvp.model.api.data.MoviesDetail
import com.androidapp.movieappmvp.model.cache.IMoviesDetailCache
import com.androidapp.movieappmvp.model.entity.room.data.RoomDetailMovie
import com.androidapp.movieappmvp.model.entity.room.db.DBMovies
import io.reactivex.Single

class CacheRoomMovieDetail(private val db: DBMovies) : IMoviesDetailCache {

    override fun getMoviesDetailForID(id: Long): Single<RoomDetailMovie> = Single.fromCallable {
        db.moviesDetail().getMovieFromID(id)
    }

    override fun putMoviesDetail(movies: MoviesDetail) {
        val movieRoom = movies.let { movie ->
            RoomDetailMovie(
                id = movie.id,
                title = movie.title,
                overview = movie.overview,
                backdrop = movie.backdropPath,
                ratings = movie.ratings,
                numberOfRatings = movie.voteCount.toInt(),
                minimumAge = if (movie.adult) 16 else 13,
                runtime = movie.runtime,
                genres = movie.genres.joinToString (", "){ it.name },
            )
        }
        db.moviesDetail().insertMovieDetail(movieRoom)
    }

    override fun getMovieActors(id: Long): Single<List<Cast>> = Single.fromCallable {
        val listCast = mutableListOf<Cast>()
        val nameActorsList = db.moviesDetail().getNameActor(id).split(SEPARATOR)
        val profilePaths = db.moviesDetail().getProfilePaths(id).split(SEPARATOR)
        nameActorsList.forEachIndexed { index, _ ->
            listCast.add(Cast(name = nameActorsList[index], profilePath = profilePaths[index]))
        }
        listCast
    }

    override fun putMoviesActors(id: Long, listCast: List<Cast>) {
        db.moviesDetail().setNameActor(listCast.joinToString(SEPARATOR){it.name}, id)
        db.moviesDetail().setProfilePaths(listCast.joinToString(SEPARATOR) {it.profilePath ?: " "}, id)
    }
}