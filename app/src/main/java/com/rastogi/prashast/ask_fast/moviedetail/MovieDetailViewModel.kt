package com.rastogi.prashast.ask_fast.moviedetail

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rastogi.prashast.ask_fast.repo.MoviesRepo
import com.rastogi.prashast.ask_fast.config.NetworkState
import com.rastogi.prashast.ask_fast.config.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieDetailViewModel : ViewModel() {

    var movie = MutableLiveData<Movie>()
    var networkState = MutableLiveData<NetworkState>()
    var moviesRepo = MoviesRepo()

    @SuppressLint("CheckResult")
    fun getMovieDetails(id: Long) {
        moviesRepo.getMovieDetail(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                movie.postValue(it)
            }, {
                networkState.postValue(
                    NetworkState(
                        if (it != null)
                            it.message!!
                        else "error", NetworkState.FAILED
                    )
                )
            })
    }
}