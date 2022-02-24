package com.example.anew.features.search

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.anew.R
import com.example.anew.data.model.Item
import com.example.anew.databinding.LayoutTrendingBinding

class TrendingAdapter :
    ListAdapter<Item, TrendingAdapter.TrendingViewHolder>(DiffCallback()) {

    inner class TrendingViewHolder(private val binding: LayoutTrendingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.item = item
            binding.root.setOnClickListener {
                item.selected = true
                binding.selected = item.selected
            }
            binding.selected = item.selected
            binding.tvLink.setOnClickListener {
                binding.root.context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(item.html_url)
                    )
                )
            }
            Glide.with(binding.root)
                .load(item.owner.avatar_url)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(binding.ivAvatar)

        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Item>() {

        //2
        override fun areItemsTheSame(oldItem: Item, newItem: Item) =
            oldItem.id == newItem.id

        //3
        override fun areContentsTheSame(oldItem: Item, newItem: Item) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {
        return TrendingViewHolder(
            LayoutTrendingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}