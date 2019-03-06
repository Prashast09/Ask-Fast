package com.rastogi.prashast.ask_fast

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rastogi.prashast.ask_fast.config.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@SuppressLint("CheckResult")

class MovieListViewModel() : ViewModel() {

    var nowPlayingMovie = MutableLiveData<ArrayList<Movie>>()
    var nowPlayingError = MutableLiveData<Throwable>()

    var popularMovie = MutableLiveData<ArrayList<Movie>>()
    var popularMovieError = MutableLiveData<Throwable>()

    var movieListRepo: MovieListRepo = MovieListRepo()

    fun getNowPlayingMovie() {
        movieListRepo.getNowPlayingMovie()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (nowPlayingMovie.value != null) {
                    val list = nowPlayingMovie.value
                    list?.addAll(it.movieList!!)
                    nowPlayingMovie.value = list
                } else
                    nowPlayingMovie.value = it.movieList
            }, {
                nowPlayingError.value = it
            })
    }


    fun getPopularMovie() {
        movieListRepo.getPopularMovie()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val list = popularMovie.value
                list?.addAll(it.movieList!!)
                popularMovie.value = list
            }, {
                popularMovieError.value = it
            })
    }


}
