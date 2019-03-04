package com.rastogi.prashast.ask_fast

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbService {

    @GET("/movie/now_playing")
    fun getNowPlayingMovies(
        @Query("api_key") apiKey: String, @Query("language") language: String,
        @Query("page") page: String
    ): Single<Movie>

    @GET("/movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String, @Query("language") language: String,
        @Query("page") page: String
    ): Single<Movie>

    @GET("/movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String, @Query("language") language: String
    )

}