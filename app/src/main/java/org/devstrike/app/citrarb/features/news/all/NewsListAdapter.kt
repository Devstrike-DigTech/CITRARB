/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.news.all

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import org.devstrike.app.citrarb.databinding.ItemNewsListLayoutBinding
import org.devstrike.app.citrarb.features.landing.data.LandingMenu
import org.devstrike.app.citrarb.features.news.newsLanding.data.remote.NewsListResponse
import javax.inject.Inject

/**
 * Created by Richard Uzor  on 23/12/2022
 */
/**
 * Created by Richard Uzor  on 23/12/2022
 */
class NewsListAdapter @Inject constructor(): PagingDataAdapter<NewsListResponse, NewsListAdapter.NewsListViewHolder>(
    NewsListComparator
) {

    val TAG = "newsListAdapter"

    inner class NewsListViewHolder(private val binding: ItemNewsListLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(listener: View.OnClickListener,item: NewsListResponse) = binding.apply {
            newsListItem = item
            newsListItemClickListener = listener
            executePendingBindings()
        }
    }

    object NewsListComparator: DiffUtil.ItemCallback<NewsListResponse>(){
        override fun areItemsTheSame(
            oldItem: NewsListResponse,
            newItem: NewsListResponse
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: NewsListResponse,
            newItem: NewsListResponse
        ) = oldItem == newItem

    }

    override fun onBindViewHolder(holder: NewsListViewHolder, position: Int) {
        val newsListItem = getItem(position)

            holder.apply {
                bind(createOnClickListener(newsListItem!!), newsListItem)
                itemView.tag = newsListItem

            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NewsListViewHolder(
        ItemNewsListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    private fun createOnClickListener(newsListItem: NewsListResponse): View.OnClickListener {
        return View.OnClickListener {
            Log.d(TAG, "createOnClickListener: ${newsListItem.title}")
        }
    }

    }