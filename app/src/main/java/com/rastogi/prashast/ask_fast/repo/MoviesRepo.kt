package com.rastogi.prashast.ask_fast.repo

import com.rastogi.prashast.ask_fast.BuildConfig
import com.rastogi.prashast.ask_fast.service.RetrofitInstance
import com.rastogi.prashast.ask_fast.config.Movie
import com.rastogi.prashast.ask_fast.config.MovieResult
import io.reactivex.Single

class MoviesRepo {


    private val service: RetrofitInstance =
        RetrofitInstance.instance


    fun getNowPlayingMovie(nowPlayingPage: Int): Single<MovieResult> {
        return service?.tmdbApi.getNowPlayingMovies(BuildConfig.API_KEY, nowPlayingPage)!!
    }

    fun getPopularMovie(popularMoviePage: Int): Single<MovieResult> {
        return service?.tmdbApi.getPopularMovies(BuildConfig.API_KEY, popularMoviePage)!!
    }

    fun searchMovie(queryPage: Int,query : String): Single<MovieResult> {
        return service?.tmdbApi.searchMovies(query, BuildConfig.API_KEY,queryPage)!!
    }

    fun getMovieDetail(movieId: Long): Single<Movie> {
        return service?.tmdbApi.getMovieDetails(movieId, BuildConfig.API_KEY)!!
    }
}