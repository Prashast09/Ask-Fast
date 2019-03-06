package com.rastogi.prashast.ask_fast

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.rastogi.prashast.ask_fast.config.Movie


class MovieDataFactory(private val movieListRepo: MovieListRepo) : DataSource.Factory<Int, Movie>() {

    val mutableLiveData: MutableLiveData<MovieDataSource> = MutableLiveData()
    private lateinit var movieDataSource: MovieDataSource

    override fun create(): DataSource<Int, Movie> {
        movieDataSource = MovieDataSource(movieListRepo)
        mutableLiveData.postValue(movieDataSource)
        return movieDataSource
    }
}