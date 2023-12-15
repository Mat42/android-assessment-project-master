package com.vp.favorites.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vp.favorites.databinding.FavoriteItemBinding
import com.vp.favorites.model.FavoriteMovie

private val diffUtil = object : DiffUtil.ItemCallback<FavoriteMovie>() {
    override fun areItemsTheSame(oldItem: FavoriteMovie, newItem: FavoriteMovie):
            Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FavoriteMovie, newItem: FavoriteMovie):
            Boolean {
        return oldItem == newItem
    }
}

class FavoriteListAdapter(val listener: OnItemClickListener): ListAdapter<FavoriteMovie, FavoriteListAdapter.FavoriteViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FavoriteItemBinding.inflate(inflater, parent, false)

        return FavoriteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.setMovie(currentList[position])
    }

    inner class FavoriteViewHolder(private val binding: FavoriteItemBinding): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                listener.onItemClick(currentList[bindingAdapterPosition])
            }
        }
        fun setMovie(movie: FavoriteMovie) {
            binding.title.text = movie.title
        }
    }

    interface OnItemClickListener {
        fun onItemClick(movie: FavoriteMovie)
    }
}