package com.rastogi.prashast.ask_fast.config

import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName

class Movie {

    @SerializedName("title")
    var title: String? = ""

    @SerializedName("vote_average")
    var rating: Float? = null


    @SerializedName("poster_path")
    var posterPath: String? = null

    @SerializedName("id")
    var id: Long? = null

    @SerializedName("backdrop_path")
    var backdropPath: String? = null

    @SerializedName("genres")
    var genres: List<Genre>? = null

    @SerializedName("release_date")
    var releaseDate: String? = null

    @SerializedName("overview")
    var overview: String? = null

    @SerializedName("runtime")
    var runTime: Int? = null

    @SerializedName("credits")
    var creditsResponse: CreditsResponse? = null

    companion object {

        var NOW_PLAYING_MOVIE = "now_playing_movie"
        var POPULAR_MOVIE = "popular_movie"
        var SEARCH_MOVIE = "search_movie"

        var DIFF_CALLBACK: DiffUtil.ItemCallback<Movie> = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.equals(newItem)
            }

        }
    }

    override fun equals(other: Any?): Boolean {
        if (other === this)
            return true

        val article = other as Movie?
        return article!!.id == this.id
    }
}