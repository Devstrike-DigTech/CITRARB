/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.news.local

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import org.devstrike.app.citrarb.databinding.ItemNewsListLayoutBinding
import org.devstrike.app.citrarb.features.news.newsLanding.NewsLandingDirections
import org.devstrike.app.citrarb.features.news.newsLanding.data.remote.NewsListResponse
import javax.inject.Inject

/**
 * Created by Richard Uzor  on 25/12/2022
 */
/**
 * Created by Richard Uzor  on 25/12/2022
 */
class LocalNewsListAdapter @Inject constructor(): PagingDataAdapter<NewsListResponse, LocalNewsListAdapter.LocalNewsListViewHolder>(
    LocalNewsListComparator
) {

    val TAG = "newsListAdapter"

    inner class LocalNewsListViewHolder(private val binding: ItemNewsListLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: View.OnClickListener, item: NewsListResponse) {
            if (item.category == "Local News"){
                binding.apply {
                    newsListItem = item
                    newsListItemClickListener = listener
                    executePendingBindings()
                }
            }
        }

    }

    object LocalNewsListComparator: DiffUtil.ItemCallback<NewsListResponse>(){
        override fun areItemsTheSame(
            oldItem: NewsListResponse,
            newItem: NewsListResponse
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: NewsListResponse,
            newItem: NewsListResponse
        ) = oldItem == newItem

    }

    override fun onBindViewHolder(holder: LocalNewsListViewHolder, position: Int) {
        val newsListItem = getItem(position)
//
//            when(newsListItem!!.category){
//                "Local News" -> {
//                    Log.d(TAG, "onBindViewHolder| local: $newsListItem")

                    holder.apply {
                        bind(createOnClickListener(newsListItem!!), newsListItem)
                        itemView.tag = newsListItem

                    }
//            }
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LocalNewsListViewHolder(
        ItemNewsListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    private fun createOnClickListener(newsListItem: NewsListResponse): View.OnClickListener {
        return View.OnClickListener {
            val navToDetail = NewsLandingDirections.actionNewsLandingToNewsDetail(newsListItem.link)
            it.findNavController().navigate(navToDetail)
        }
    }

}
