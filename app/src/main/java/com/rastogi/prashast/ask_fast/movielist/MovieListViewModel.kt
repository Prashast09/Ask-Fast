package com.rastogi.prashast.ask_fast.movielist

import android.annotation.SuppressLint
import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.rastogi.prashast.ask_fast.config.Movie
import com.rastogi.prashast.ask_fast.config.NetworkState
import com.rastogi.prashast.ask_fast.data.MovieDataFactory
import com.rastogi.prashast.ask_fast.repo.MoviesRepo
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@SuppressLint("CheckResult")

class MovieListViewModel : ViewModel() {

    lateinit var nowPlayingMovie: LiveData<PagedList<Movie>>

    lateinit var networkState: LiveData<NetworkState>

    var moviesRepo: MoviesRepo = MoviesRepo()
    private var executor: Executor? = null
    private var pagedListConfig: PagedList.Config
    private lateinit var movieDataFactory: MovieDataFactory

    init {
        executor = Executors.newFixedThreadPool(5)

        pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(10)
            .setPageSize(20).build()

        getNowPlayingMovies()

    }

    fun getNowPlayingMovies() {
        initMovieDataSource(null, Movie.NOW_PLAYING_MOVIE)
    }

    fun getPopularMovies() {
        initMovieDataSource(null, Movie.POPULAR_MOVIE)

    }

    fun getSearchedMovies(it: String) {
        initMovieDataSource(it, Movie.SEARCH_MOVIE)
    }

    private fun initMovieDataSource(query: String?, type: String) {

        initDataFactory(type)
        initSearchString(query)
        invalidateDataFactory()
        initNetworkState()
        initPageBuilder()

    }

    private fun initNetworkState() {
        networkState = Transformations.switchMap(movieDataFactory.mutableLiveData)
        { dataSource -> dataSource.networkState }
    }

    private fun initDataFactory(type: String) {
        if (!::movieDataFactory.isInitialized)
            movieDataFactory = MovieDataFactory(moviesRepo, type)
        else
            movieDataFactory.setMovieType(type)
    }

    private fun initSearchString(query: String?) {
        if (!TextUtils.isEmpty(query))
            movieDataFactory.setQueryString(query!!)
    }

    private fun invalidateDataFactory() {
        if (::nowPlayingMovie.isInitialized)
            movieDataFactory.invalidate()
    }

    private fun initPageBuilder() {
        nowPlayingMovie = LivePagedListBuilder(movieDataFactory, pagedListConfig).setFetchExecutor(executor!!).build()
    }


}
