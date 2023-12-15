package com.vp.detail

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.vp.detail.databinding.ActivityDetailBinding
import com.vp.detail.viewmodel.DetailsViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import kotlin.run

class DetailActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private lateinit var detailViewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        detailViewModel = ViewModelProvider(this, factory).get(DetailsViewModel::class.java)
        detailViewModel.init(getMovieId())
        binding.viewModel = detailViewModel
        binding.lifecycleOwner = this
        detailViewModel.title().observe(this, Observer {
            supportActionBar?.title = it
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.star -> detailViewModel.updateFavoriteStatus()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getMovieId(): String {
        return intent?.data?.getQueryParameter(IMDB_ID_PARAM) ?: run {
            throw IllegalStateException("You must provide movie id to display details")
        }
    }

    companion object {
        const val IMDB_ID_PARAM = "imdbID"
    }
}
