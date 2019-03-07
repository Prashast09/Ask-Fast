package com.rastogi.prashast.ask_fast

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.rastogi.prashast.ask_fast.config.Movie
import com.rastogi.prashast.ask_fast.config.Movie.Companion.SEARCH_MOVIE
import com.rastogi.prashast.ask_fast.config.MovieResult
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@SuppressLint("CheckResult")
class MovieDataSource(val moviesRepo: MoviesRepo, var movieType: String, var query: String) :
    PageKeyedDataSource<Int, Movie>() {


    var networkState = MutableLiveData<NetworkState>()


    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
        networkState.postValue(NetworkState("Loading", NetworkState.LOADING))

        val movieList: Single<MovieResult> =
            when (movieType) {
                Movie.NOW_PLAYING_MOVIE -> moviesRepo.getNowPlayingMovie(1)
                SEARCH_MOVIE -> moviesRepo.searchMovie(1, query)
                else -> moviesRepo.getPopularMovie(1)
            }
        movieList.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback.onResult(it.movieList!!, null, 2)
                networkState.postValue(NetworkState("Loaded", NetworkState.LOADED))
            }, {
                networkState.postValue(
                    NetworkState(
                        if (it == null) "unknown error" else it.message!!,
                        NetworkState.FAILED
                    )
                )
            })


    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        networkState.postValue(NetworkState("Loading", NetworkState.LOADING))

        val movieList: Single<MovieResult> = when (movieType) {
            Movie.NOW_PLAYING_MOVIE -> moviesRepo.getNowPlayingMovie(params.key)
            SEARCH_MOVIE -> moviesRepo.searchMovie(params.key, query)
            else -> moviesRepo.getPopularMovie(params.key)
        }

        movieList.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val nextKey = if (params.key == it.totalPages)
                    null
                else
                    params.key + 1
                callback.onResult(it.movieList!!, nextKey)
            }, {
                networkState.postValue(
                    NetworkState(
                        if (it == null) "unknown error" else it.message!!,
                        NetworkState.FAILED
                    )
                )
            })

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
    }

}