/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.tv.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.devstrike.app.citrarb.databinding.ItemTvVideoListLayoutBinding
import org.devstrike.app.citrarb.features.tv.data.model.TvVideo


class TvVideoListAdapter(val context: Context): ListAdapter<TvVideo, TvVideoListAdapter.ItemViewHolder>(TvVideoDiffCallback) {

    class ItemViewHolder(val binding: ItemTvVideoListLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemTvVideoListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val tvVideo = getItem(position)
        holder.binding.apply{
            videoItemTitle.text = tvVideo.title
            videoItemDateTimeTxt.text = tvVideo.publishedAt
                .replace("T", " | ")
                .removeSuffix("Z")

            val videoThumbnailUrl = tvVideo.thumbnails.high.url
            Glide.with(context)
                .load(videoThumbnailUrl)
                .centerCrop()
                .into(videoThumbnail)
            
        }
    }

    object TvVideoDiffCallback: DiffUtil.ItemCallback<TvVideo>() {
        override fun areItemsTheSame(oldItem: TvVideo, newItem: TvVideo): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: TvVideo, newItem: TvVideo): Boolean {
            return oldItem == newItem
        }

    }
}