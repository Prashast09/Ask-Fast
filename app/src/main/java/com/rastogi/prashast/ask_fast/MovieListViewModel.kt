package com.rastogi.prashast.ask_fast

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.rastogi.prashast.ask_fast.config.Movie
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@SuppressLint("CheckResult")

class MovieListViewModel() : ViewModel() {

    lateinit var nowPlayingMovie: LiveData<PagedList<Movie>>
    var nowPlayingError = MutableLiveData<Throwable>()

    var networkState: LiveData<NetworkState>? = null


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
        if (!::movieDataFactory.isInitialized)
            movieDataFactory = MovieDataFactory(moviesRepo, "now_playing_movie")
        else
            movieDataFactory.setMovieType("now_playing_movie")

        if (::nowPlayingMovie.isInitialized)
           movieDataFactory.invalidate()
        networkState = Transformations.switchMap(movieDataFactory.mutableLiveData)
        { dataSource -> dataSource.networkState }

        nowPlayingMovie = LivePagedListBuilder(movieDataFactory, pagedListConfig).setFetchExecutor(executor!!).build()

    }

    fun getPopularMovies() {
        if (!::movieDataFactory.isInitialized)
        movieDataFactory = MovieDataFactory(moviesRepo, "popular_movies")
        else
            movieDataFactory.setMovieType("popular_movies")

        if (::nowPlayingMovie.isInitialized)
            movieDataFactory.invalidate()

        networkState = Transformations.switchMap(movieDataFactory.mutableLiveData)
        { dataSource -> dataSource.networkState }

        nowPlayingMovie = LivePagedListBuilder(movieDataFactory, pagedListConfig).setFetchExecutor(executor!!).build()

    }

    fun getSearchedMovies(it: String) {
        if (!::movieDataFactory.isInitialized)
            movieDataFactory = MovieDataFactory(moviesRepo, "search_movies")
        else
            movieDataFactory.setMovieType("search_movies")

        movieDataFactory.setQueryString(it)

        if (::nowPlayingMovie.isInitialized)
            movieDataFactory.invalidate()

        networkState = Transformations.switchMap(movieDataFactory.mutableLiveData)
        { dataSource -> dataSource.networkState }

        nowPlayingMovie = LivePagedListBuilder(movieDataFactory, pagedListConfig).setFetchExecutor(executor!!).build()
    }


}
