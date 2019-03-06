package com.rastogi.prashast.ask_fast.config

import com.google.gson.annotations.SerializedName

class Movie {

    @SerializedName("title")
    var title: String? = ""

    @SerializedName("vote_average")
    var rating: Float? = null


    @SerializedName("backdrop_path")
    var posterPath: String? = null
}