/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.tv.ui.tvdetail

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import org.devstrike.app.citrarb.R
import org.devstrike.app.citrarb.databinding.CustomTvViewPagerItemBinding
import org.devstrike.app.citrarb.features.tv.data.TVVideos
import org.devstrike.app.citrarb.features.tv.data.model.TVListItem
import org.devstrike.app.citrarb.features.tv.ui.tvlist.TVListAdapter
import org.devstrike.app.citrarb.utils.Common
import org.devstrike.app.citrarb.utils.Common.tvVideos
import kotlin.properties.Delegates

/**
 * Created by Richard Uzor  on 18/01/2023
 */
/**
 * Created by Richard Uzor  on 18/01/2023
 */
//class TVDetailAdapter(
//    internal val context: Context,
//    internal val videosList: List<TVVideos>,
//    container: ViewGroup,
//    itemView: View = LayoutInflater.from(context)
//        .inflate(R.layout.custom_tv_view_pager_item, container, false)
//) : PagerAdapter(), View.OnClickListener {
//
//    //private val args: TVVideos
//    //get() = args.videoDetail
//
//
//    var inflater: LayoutInflater = LayoutInflater.from(context)
////    var itemPosition by Delegates.notNull<Int>()
//
//    var youTubePlayer: YouTubePlayerView
//
//    init {
//        youTubePlayer = itemView.findViewById<View>(R.id.third_party_player_view) as YouTubePlayerView
//    }
//
//    override fun getCount(): Int {
//        return tvVideos.size
//    }
//
//    override fun isViewFromObject(view: View, `object`: Any): Boolean {
//        return view == `object`
//    }
//
//    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
//        container.removeView(view as View)
//    }
//
//    override fun instantiateItem(container: ViewGroup, position: Int): Any {
//        val view =
//            inflater.inflate(R.layout.custom_tv_view_pager_item, container, false) as ViewGroup
//
//        youTubePlayer = view.findViewById<View>(R.id.third_party_player_view) as YouTubePlayerView
//
//        val itemPosition = position
//
////        youTubePlayer.getPlayerUiController().showFullscreenButton(true)
//        youTubePlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
//            override fun onReady(youTubePlayer: YouTubePlayer) {
//                val vidId = tvVideos.get(position).videoLink
//                youTubePlayer.cueVideo(vidId, 0f)
//            }
//        })
//
//        container.addView(view)
//        return view
//    }
//
//    override fun onClick(p0: View?) {
//        TODO("Not yet implemented")
//    }
//}

class TVDetailAdapter : ListAdapter<TVVideos, TVDetailAdapter.ViewHolder>(DiffCallback()) {

    val TAG = "ViewPagerAdapter"
    val videosList = Common.tvVideos

    class ViewHolder(val binding: CustomTvViewPagerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(itemData: TVVideos){
                binding.apply {
                    tvDetailViewPagerItem = itemData
                    executePendingBindings()
                }
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CustomTvViewPagerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val detailVideo = getItem(position)
        holder.apply {
            bind(detailVideo)
        }.also {
            Log.d(TAG, "onBindViewHolder: ${detailVideo.videoLink}")
            holder.binding.thirdPartyPlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val vidId = detailVideo.videoLink//.toUri().encodedQuery!!.drop(2)
                youTubePlayer.cueVideo(vidId, 0f)
            }
        })
        }
    }

    override fun getItemCount(): Int {
        return videosList.size
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