/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.news.national

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import org.devstrike.app.citrarb.databinding.ItemNewsListLayoutBinding
import org.devstrike.app.citrarb.features.news.newsLanding.data.remote.NewsListResponse
import javax.inject.Inject

/**
 * Created by Richard Uzor  on 25/12/2022
 */
/**
 * Created by Richard Uzor  on 25/12/2022
 */
class NationalNewsListAdapter @Inject constructor(): PagingDataAdapter<NewsListResponse, NationalNewsListAdapter.NationalNewsListViewHolder>(
    NationalNewsListComparator
) {

    val TAG = "newsListAdapter"

    inner class NationalNewsListViewHolder(private val binding: ItemNewsListLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: View.OnClickListener, item: NewsListResponse) {
            if (item.category == "National"){
                binding.apply {
                    newsListItem = item
                    newsListItemClickListener = listener
                    executePendingBindings()
                }
            }

        }
    }

    object NationalNewsListComparator: DiffUtil.ItemCallback<NewsListResponse>(){
        override fun areItemsTheSame(
            oldItem: NewsListResponse,
            newItem: NewsListResponse
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: NewsListResponse,
            newItem: NewsListResponse
        ) = oldItem == newItem

    }

    override fun onBindViewHolder(holder: NationalNewsListViewHolder, position: Int) {
        val newsListItem = getItem(position)

        when(newsListItem!!.category){
            "National" -> {
                Log.d(TAG, "onBindViewHolder| local: $newsListItem")

                holder.apply {
                    bind(createOnClickListener(newsListItem!!), newsListItem)
                    itemView.tag = newsListItem

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NationalNewsListViewHolder(
        ItemNewsListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    private fun createOnClickListener(newsListItem: NewsListResponse): View.OnClickListener {
        return View.OnClickListener {
            Log.d(TAG, "createOnClickListener: ${newsListItem.title}")
        }
    }

}