package com.rastogi.prashast.ask_fast

import com.rastogi.prashast.ask_fast.config.Movie
import com.rastogi.prashast.ask_fast.config.MovieResult
import io.reactivex.Single

class MovieListRepo {




    private val service: RetrofitInstance = RetrofitInstance.instance


    fun getNowPlayingMovie(nowPlayingPage : Int): Single<MovieResult> {
        return service?.tmdbApi.getNowPlayingMovies(BuildConfig.API_KEY, nowPlayingPage)!!
    }

//    fun getPopularMovie(): Single<MovieResult> {
//        return service?.tmdbApi.getPopularMovies(BuildConfig.API_KEY, popularMoviesPage)!!
//    }

    fun getMovieDetail(movieId: String): Single<Movie> {
        return service?.tmdbApi.getMovieDetails(movieId, BuildConfig.API_KEY)!!
    }
}