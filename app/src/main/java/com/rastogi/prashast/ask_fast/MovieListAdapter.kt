package com.rastogi.prashast.ask_fast

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.rastogi.prashast.ask_fast.config.Movie

class MovieListAdapter(
    var context: Context?,
    var movieList: ArrayList<Movie>
) : androidx.recyclerview.widget.RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_in_list, parent, false)
        val viewHolder = MovieListViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(viewHolder: MovieListViewHolder, position: Int) {
        viewHolder.bindDataToView(context, movieList.get(position))
    }

    fun setData(it: ArrayList<Movie>?) {
        movieList = it!!
        notifyDataSetChanged()
    }


    class MovieListViewHolder(val view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        fun bindDataToView(context: Context?, movie: Movie) {
            Glide.with(context!!).load("https://image.tmdb.org/t/p/w500"+movie.posterPath).into(view.findViewById<ImageView>(R.id.movie_iv));


            view.findViewById<TextView>(R.id.movie_name_tv).setText(movie.title)
            view.findViewById<TextView>(R.id.rating_tv).setText(movie.rating.toString())
        }

    }
}