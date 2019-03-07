package com.rastogi.prashast.ask_fast.movielist

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.rastogi.prashast.ask_fast.R
import com.rastogi.prashast.ask_fast.util.UiUtils
import com.rastogi.prashast.ask_fast.config.Movie
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.movie_list_fragment.*
import java.util.concurrent.TimeUnit


class MovieListFragment : androidx.fragment.app.Fragment(), MenuItem.OnActionExpandListener {


    private lateinit var movieListAdapter: MovieListAdapter
    private var type: String = Movie.NOW_PLAYING_MOVIE

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
        setHasOptionsMenu(true)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main, menu)
        UiUtils.tintMenuIcon(
            context!!,
            menu.findItem(R.id.action_sort_by),
            R.color.md_white_1000
        )
        initSearchView(menu)

    }

    private fun initSearchView(menu: Menu) {
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = MenuItemCompat.getActionView(searchItem) as SearchView
        intializeSearch(searchView)
        searchItem.setOnActionExpandListener(this)
    }

    override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
        return true
    }

    override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
        if (type == Movie.NOW_PLAYING_MOVIE)
            viewModel.getNowPlayingMovies()
        else if (type == Movie.POPULAR_MOVIE)
            viewModel.getPopularMovies()
        return true
    }


    private fun intializeSearch(searchView: SearchView) {
        RxSearchObservable.fromView(searchView)
            .debounce(300, TimeUnit.MILLISECONDS)
            .filter { !it.isEmpty() }
            .distinctUntilChanged()
            .subscribe { viewModel.getSearchedMovies(it) }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.groupId == R.id.menu_sort_group) {
            if (item.itemId == R.id.now_playing_movie) {
                viewModel.getNowPlayingMovies()
                type = Movie.NOW_PLAYING_MOVIE
            } else if (item.itemId == R.id.popular_movie) {
                viewModel.getPopularMovies()
                type = Movie.POPULAR_MOVIE
            }
            item.isChecked = true
        }

        return super.onOptionsItemSelected(item)
    }


    object RxSearchObservable {

        fun fromView(searchView: SearchView): Observable<String> {

            val subject = PublishSubject.create<String>()

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(s: String): Boolean {
                    subject.onComplete()
                    return true
                }

                override fun onQueryTextChange(text: String): Boolean {
                    subject.onNext(text)
                    return true
                }
            })

            return subject
        }
    }

}
