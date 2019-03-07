package com.rastogi.prashast.ask_fast

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.rastogi.prashast.ask_fast.config.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@SuppressLint("CheckResult")
class MovieDataSource(val moviesRepo: MoviesRepo) : PageKeyedDataSource<Int, Movie>() {


    var networkState = MutableLiveData<NetworkState>()


    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
        networkState?.postValue(NetworkState("Loading", NetworkState.LOADING))
        moviesRepo.getNowPlayingMovie(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback.onResult(it.movieList!!, null, 2)
                networkState!!.postValue(NetworkState("Loaded", NetworkState.LOADED))
            }, {
                networkState!!.postValue(
                    NetworkState(
                        if (it == null) "unknown error" else it.message!!,
                        NetworkState.FAILED
                    )
                )
            })


    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        networkState?.postValue(NetworkState("Loading",NetworkState.LOADING))

        moviesRepo.getNowPlayingMovie(params.key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val nextKey = if (params.key === it.totalPages)
                    null
                else
                    params.key + 1
                callback.onResult(it.movieList!!, nextKey)
            }, {
                networkState!!.postValue(
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