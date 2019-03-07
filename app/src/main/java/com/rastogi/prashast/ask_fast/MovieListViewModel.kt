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

    var popularMovie = MutableLiveData<PagedList<Movie>>()
    var popularMovieError = MutableLiveData<Throwable>()

     var networkState: LiveData<NetworkState>? = null


    var moviesRepo: MoviesRepo = MoviesRepo()
    private var executor: Executor? = null


    init {
        executor = Executors.newFixedThreadPool(5)

        val feedDataFactory = MovieDataFactory(moviesRepo)
        networkState = Transformations.switchMap(feedDataFactory.mutableLiveData)
        { dataSource -> dataSource.networkState }

        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(10)
            .setPageSize(20).build()

        nowPlayingMovie = LivePagedListBuilder(feedDataFactory, pagedListConfig).setFetchExecutor(executor!!).build()
    }


}
