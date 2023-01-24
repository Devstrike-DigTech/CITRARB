/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.tv.ui.tvlist

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.devstrike.app.citrarb.databinding.ItemTvListLayoutBinding
import org.devstrike.app.citrarb.features.tv.data.TVVideos
import org.devstrike.app.citrarb.features.tv.data.model.TVListItem
import org.devstrike.app.citrarb.features.tv.ui.tvdetail.TVDetailArgs
import org.devstrike.app.citrarb.utils.Common
import org.devstrike.app.citrarb.utils.Common.TAG
import org.devstrike.app.citrarb.utils.loadImage
import javax.inject.Inject


class TVListAdapter @Inject constructor(val context: Context) :
    ListAdapter<TVVideos, TVListAdapter.TVListViewHolder>(DiffCallback()) {


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
            holder.binding.videoThumbnail.loadImage(tvVideo.videoThumbnail)

        }
    }

    class TVListViewHolder(val binding: ItemTvListLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: View.OnClickListener, itemData: TVVideos) {
            binding.apply {
                tvListItem = itemData
                tvItemClickListener = listener
                executePendingBindings()
            }
        }
    }


    private fun createOnClickListener(tvListItem: TVVideos): View.OnClickListener {
        return View.OnClickListener {
            Log.d(TAG, "createOnClickListener: ${tvListItem.videoLink} clicked ")
            Log.d(TAG, "createOnClickListener: ${tvListItem.videoPublishedDate} clicked ")
            val videoLink = tvListItem.videoLink//.toUri().encodedQuery!!.drop(2)

            val tvVideo = TVVideos(
                videoTitle = tvListItem.videoTitle,
                videoDescription = tvListItem.videoDescription,
                videoLink = videoLink,
                videoPublishedDate = tvListItem.videoPublishedDate
                    .replace("T", " | ")
                    .removeSuffix("Z"),
                videoThumbnail = tvListItem.videoThumbnail
            )

           // for (video in Common.tvVideos){
//                if (video.videoLink == videoLink){
//                    Common.tvVideos.remove(video)
//                }
            //}
            Common.tvVideos.add(0, tvVideo)
//            Common.tvVideos.add(tvVideo)

            val currentVideoIndex = Common.tvVideos.indexOf(tvVideo)

            Log.d(TAG, "createOnClickListener new: ${Common.tvVideos}")

            val navToTVDetail = TvVideoListDirections.actionTVVideoListToTVDetail(tvVideo)
            it.findNavController().navigate(navToTVDetail)

            Log.d(
                TAG,
                "createOnClickListener: $videoLink "
            )

//            val navToDetail = NewsLandingDirections.actionNewsLandingToNewsDetail(
//                newsListItem.link,
//                newsListItem.author,
//                newsListItem
//            )
//            it.findNavController().navigate(navToDetail)
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<TVVideos>() {
        override fun areItemsTheSame(oldItem: TVVideos, newItem: TVVideos): Boolean {
            return oldItem.videoLink == newItem.videoLink
        }

        override fun areContentsTheSame(oldItem: TVVideos, newItem: TVVideos): Boolean {
            return oldItem == newItem
        }

    }
}