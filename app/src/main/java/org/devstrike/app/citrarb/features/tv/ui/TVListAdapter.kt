/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.tv.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.devstrike.app.citrarb.databinding.ItemTvListLayoutBinding
import org.devstrike.app.citrarb.features.tv.data.model.TVListItem
import org.devstrike.app.citrarb.utils.Common.TAG
import org.devstrike.app.citrarb.utils.loadImage
import javax.inject.Inject


class TVListAdapter @Inject constructor(val context: Context) :
    ListAdapter<TVListItem, TVListAdapter.TVListViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVListViewHolder {
        return TVListViewHolder(
            ItemTvListLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TVListViewHolder, position: Int) {
        val tvVideo = getItem(position)
        holder.apply {
            bind(createOnClickListener(tvVideo), tvVideo)
        }.also {
            holder.binding.videoThumbnail.loadImage(tvVideo.thumbnails.default!!.url)
            tvVideo.publishedAt
                .replace("T", " | ")
                .removeSuffix("Z")
        }
    }

    class TVListViewHolder(val binding: ItemTvListLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: View.OnClickListener, itemData: TVListItem) {
            binding.apply {
                tvListItem = itemData
                tvItemClickListener = listener
                executePendingBindings()
            }
        }
    }


    private fun createOnClickListener(tvListItem: TVListItem): View.OnClickListener {
        return View.OnClickListener {
            Log.d(TAG, "createOnClickListener: ${tvListItem.title} clicked ")
            Log.d(TAG, "createOnClickListener: ${tvListItem.publishedAt} clicked ")
//            val navToDetail = NewsLandingDirections.actionNewsLandingToNewsDetail(
//                newsListItem.link,
//                newsListItem.author,
//                newsListItem
//            )
//            it.findNavController().navigate(navToDetail)
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<TVListItem>() {
        override fun areItemsTheSame(oldItem: TVListItem, newItem: TVListItem): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: TVListItem, newItem: TVListItem): Boolean {
            return oldItem == newItem
        }

    }
}