package com.rastogi.prashast.ask_fast.movielist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import com.bumptech.glide.Glide
import com.rastogi.prashast.ask_fast.R
import com.rastogi.prashast.ask_fast.config.Movie
import com.rastogi.prashast.ask_fast.moviedetail.MovieDetailActivity

class MovieListAdapter(
    var context: Context?

) : PagedListAdapter<Movie, MovieListAdapter.MovieListViewHolder>(Movie.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_in_list, parent, false)
        return MovieListViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: MovieListViewHolder, position: Int) {
        viewHolder.bindDataToView(context, getItem(position)!!)
    }

    class MovieListViewHolder(val view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        fun bindDataToView(context: Context?, movie: Movie) {
            Glide.with(context!!).load("https://image.tmdb.org/t/p/w500" + movie.posterPath)
                .into(view.findViewById(R.id.movie_iv));
            view.findViewById<TextView>(R.id.movie_name_tv).text = movie.title
            view.findViewById<TextView>(R.id.rating_tv).text = movie.rating.toString()

            attachListener(view.context, movie.id)
        }

        private fun attachListener(context: Context, id: Long?) {
            view.setOnClickListener {
                MovieDetailActivity.start(context, id!!)
            }
        }


    }
}