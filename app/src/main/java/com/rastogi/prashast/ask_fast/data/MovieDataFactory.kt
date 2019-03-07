package com.rastogi.prashast.ask_fast.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.rastogi.prashast.ask_fast.config.Movie
import com.rastogi.prashast.ask_fast.repo.MoviesRepo


class MovieDataFactory(
    private val moviesRepo: MoviesRepo,
    private var movieType: String
) : DataSource.Factory<Int, Movie>() {

    val mutableLiveData: MutableLiveData<MovieDataSource> = MutableLiveData()
    private lateinit var movieDataSource: MovieDataSource
    private var query: String = ""

    fun setMovieType(type: String) {
        movieDataSource.movieType = type
        this.movieType = type
    }

    fun setQueryString(query: String) {
        this.query = query
    }

    override fun create(): DataSource<Int, Movie> {
        movieDataSource = MovieDataSource(moviesRepo, movieType, query)
        mutableLiveData.postValue(movieDataSource)
        return movieDataSource
    }

    fun invalidate() {
        movieDataSource.invalidate()
    }
}