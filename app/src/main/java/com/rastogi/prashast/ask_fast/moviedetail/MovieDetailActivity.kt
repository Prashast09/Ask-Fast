package com.rastogi.prashast.ask_fast.moviedetail

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.rastogi.prashast.ask_fast.R
import com.rastogi.prashast.ask_fast.util.UiUtils
import com.rastogi.prashast.ask_fast.config.CreditsResponse
import com.rastogi.prashast.ask_fast.config.Genre
import com.rastogi.prashast.ask_fast.config.Movie
import kotlinx.android.synthetic.main.movie_detail_activity.*
import kotlinx.android.synthetic.main.partial_details_info.*

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: MovieDetailViewModel


    companion object {
        fun start(context: Context, movieId: Long) {
            var intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra("movie_id", movieId)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_detail_activity)
        viewModel = ViewModelProviders.of(this).get(MovieDetailViewModel::class.java)
        viewModel.getMovieDetails(intent.getLongExtra("movie_id", 0))

        viewModel.movie.observe(this, Observer {
            initViews(it)
        })

        setupToolbar()

    }

    private fun setupToolbar() {

        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            handleCollapsedToolbarTitle()
        }
    }


    private fun handleCollapsedToolbarTitle() {
        appbar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            internal var isShow = true
            internal var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                // verify if the toolbar is completely collapsed and set the movie name as the title
                if (scrollRange + verticalOffset == 0) {
                    collapsing_toolbar.title = viewModel.movie.value?.title
                    isShow = true
                } else if (isShow) {
                    // display an empty string when toolbar is expanded
                    collapsing_toolbar.title = " "
                    isShow = false
                }
            }
        })
    }

    private fun initViews(movie: Movie?) {
        Glide.with(baseContext).load("https://image.tmdb.org/t/p/w500" + movie!!.backdropPath)
            .into(image_movie_backdrop)

        Glide.with(baseContext).load("https://image.tmdb.org/t/p/w500" + movie.posterPath)
            .into(image_poster)

        text_title.text = movie.title

        setItems(chip_group, movie.genres)

        text_release_date.text = movie.releaseDate

        text_vote.text = movie.rating.toString()

        text_overview.text = movie.overview

        text_language.text = movie.runTime.toString() + " mins"

        setupCastAdapter(movie.creditsResponse)


    }

    private fun setupCastAdapter(creditsResponse: CreditsResponse?) {

        list_cast.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        list_cast.adapter = CastAdapter(creditsResponse?.castList!!.take(5))
        ViewCompat.setNestedScrollingEnabled(list_cast, false)
    }

    fun setItems(view: ChipGroup, genres: List<Genre>?) {
        if (genres == null || view.childCount > 0)
            return

        // dynamically create & add genre chips
        val context = view.context
        for (genre in genres) {
            val chip = Chip(context)
            chip.text = genre.name
            chip.chipStrokeWidth = UiUtils.dipToPixels(context, 1f)
            chip.chipStrokeColor = ColorStateList.valueOf(
                context.resources.getColor(R.color.colorPrimary)
            )
            chip.setChipBackgroundColorResource(android.R.color.transparent)
            view.addView(chip)
        }
    }
}