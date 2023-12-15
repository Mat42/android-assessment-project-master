package com.vp.favorites

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.vp.favorites.databinding.ActivityFavoriteBinding
import com.vp.favorites.model.FavoriteMovie
import com.vp.favorites.ui.adapter.FavoriteListAdapter
import com.vp.favorites.viewmodel.FavoriteViewModel
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteActivity: DaggerAppCompatActivity(), FavoriteListAdapter.OnItemClickListener {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, factory).get()

        val adapter = FavoriteListAdapter(this)
        binding.favorites.adapter = adapter
        binding.favorites.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        lifecycleScope.launch {
            viewModel.movies.collectLatest { movies ->
                adapter.submitList(movies)
            }
        }
    }

    override fun onItemClick(movie: FavoriteMovie) {
        val uri = Uri.parse("app://movies/detail")
            .buildUpon()
            .appendQueryParameter("imdbID", movie.id)
            .build()
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
}