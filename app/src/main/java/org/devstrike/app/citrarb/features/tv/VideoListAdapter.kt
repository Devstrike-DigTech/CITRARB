/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.tv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.devstrike.app.citrarb.databinding.ItemVideoListLayoutBinding

class VideoListAdapter: RecyclerView.Adapter<VideoListAdapter.ItemViewHolder>() {

    class ItemViewHolder(val binding: ItemVideoListLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemVideoListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.binding.apply{

        }
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}