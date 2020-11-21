package com.rastogi.prashast.ask_fast.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rastogi.prashast.ask_fast.R
import com.rastogi.prashast.ask_fast.movielist.MovieListFragment

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init(savedInstanceState)
    }

    fun init(savedInstanceState: Bundle?) {
        setContentView(R.layout.dashboard_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.container, MovieListFragment.newInstance())
                    .commitNow()
        }
    }

}
