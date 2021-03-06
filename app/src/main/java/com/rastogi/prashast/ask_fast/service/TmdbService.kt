package com.rastogi.prashast.ask_fast.service

import com.rastogi.prashast.ask_fast.config.Movie
import com.rastogi.prashast.ask_fast.config.MovieResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbService {

    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Single<MovieResult>?

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Single<MovieResult>?

    @GET("movie/{movie_id}?append_to_response=credits")
    fun getMovieDetails(
        @Path("movie_id") movieId: Long,
        @Query("api_key") apiKey: String
    ): Single<Movie>?

    @GET("search/movie")
    fun searchMovies(
        @Query("query") query: String,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Single<MovieResult>?

}