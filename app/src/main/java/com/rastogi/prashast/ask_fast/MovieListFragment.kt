package com.rastogi.prashast.ask_fast

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.movie_list_fragment.*

class MovieListFragment : androidx.fragment.app.Fragment() {

    private lateinit var movieListAdapter: MovieListAdapter

    companion object {
        fun newInstance() = MovieListFragment()
    }

    private lateinit var viewModel: MovieListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.movie_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MovieListViewModel::class.java)

        initViews()
        initObservers()

    }

    private fun initObservers() {
        viewModel.nowPlayingMovie.observe(this, Observer {
           movieListAdapter.submitList(it)
        })
    }

    private fun initViews() {
        movieListAdapter = MovieListAdapter(context)
        movie_list_rv.adapter = movieListAdapter
        val layoutManager = GridLayoutManager(activity, 3)
        movie_list_rv.layoutManager =
                layoutManager
    }

}
