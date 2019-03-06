package com.rastogi.prashast.ask_fast.config

import com.google.gson.annotations.SerializedName
import com.rastogi.prashast.ask_fast.config.Movie

class MovieResult {
    @SerializedName("results")
    var movieList: ArrayList<Movie>? = null

}