package com.rastogi.prashast.ask_fast

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.rastogi.prashast.ask_fast.config.Movie


class MovieDataFactory(private val moviesRepo: MoviesRepo,
                       private var movieType : String) : DataSource.Factory<Int, Movie>() {



    val mutableLiveData: MutableLiveData<MovieDataSource> = MutableLiveData()
    private lateinit var movieDataSource: MovieDataSource

    fun setMovieType(type: String){
        movieDataSource.movieType = type
        this.movieType = type
    }

    override fun create(): DataSource<Int, Movie> {
        movieDataSource = MovieDataSource(moviesRepo,movieType)
        mutableLiveData.postValue(movieDataSource)
        return movieDataSource
    }

    public fun invalidate(){
        movieDataSource.invalidate()
    }
}