/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.news.saved

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import org.devstrike.app.citrarb.databinding.ItemSavedNewsListLayoutBinding

import org.devstrike.app.citrarb.features.news.newsLanding.data.local.SavedNewsListData

/**
 * Adapter class for the locally saved news
 * it defines the recycler view implementation for the locally saved news
 * Created by Richard Uzor  on 27/12/2022
 */

class SavedNewsAdapter : RecyclerView.Adapter<SavedNewsAdapter.SavedNewsViewHolder>() {

    class SavedNewsViewHolder(val binding: ItemSavedNewsListLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    //implement diff util object to perform list similarity checks and updates
    val diffUtil = object : DiffUtil.ItemCallback<SavedNewsListData>() {
        override fun areItemsTheSame(
            oldItem: SavedNewsListData,
            newItem: SavedNewsListData
        ): Boolean {
            return oldItem.uid == newItem.uid
        }

        override fun areContentsTheSame(
            oldItem: SavedNewsListData,
            newItem: SavedNewsListData
        ): Boolean {
            return oldItem == newItem
        }

    }


    //implement the getter and setter for the diff util with the notes
    val differ = AsyncListDiffer(this, diffUtil)
    var savedNews: List<SavedNewsListData>
        get() = differ.currentList
        set(value) = differ.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedNewsViewHolder {
        return SavedNewsViewHolder(
            ItemSavedNewsListLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SavedNewsViewHolder, position: Int) {
        val news = savedNews[position]
        holder.binding.apply {
            news.title.let {
                newsItemNewsTitle.text = it
            }
            news.description.let {
                newsItemNewsDescription.text = it
            }
//            news.newsListInfo.cover_photo_small_size.let {
//                newsItemNewsDescription.setImageDraBackgroundResource(R.drawable.citrarb_logo)
//            }
            news.savedDate.let {
                newsItemDateTimeTxt.text = it
            }
            root.setOnClickListener {
                onItemClickListener?.invoke(news)
            }
        }
    }


    //create click listener for the note items to always be passed as a parameter of the event
    private var onItemClickListener: ((SavedNewsListData) -> Unit)? = null
    fun setOnItemClickListener(listener: (SavedNewsListData) -> Unit) {
        onItemClickListener = listener
    }

    override fun getItemCount(): Int {
        return savedNews.size
    }
}