/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.members.allmembers

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
import org.devstrike.app.citrarb.databinding.ItemAllMembersLayoutBinding
import org.devstrike.app.citrarb.features.members.data.models.responses.Member
import org.devstrike.app.citrarb.features.tv.data.TVVideos
import org.devstrike.app.citrarb.features.tv.data.model.TVListItem
import org.devstrike.app.citrarb.features.tv.ui.tvlist.TvVideoListDirections
import org.devstrike.app.citrarb.utils.Common
import javax.inject.Inject

/**
 * Created by Richard Uzor  on 23/01/2023
 */

class AllMembersAdapter @Inject constructor(
    val context: Context
) : ListAdapter<Member, AllMembersAdapter.AllMemberListViewHolder>(DiffCallback()) {


    class AllMemberListViewHolder(val binding: ItemAllMembersLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: View.OnClickListener, itemData: Member) {
            binding.apply {
                allMembersItem = itemData
                itemSendRequestClickListener = listener
                executePendingBindings()
            }
        }
    }


    private fun createOnClickListener(member: Member): View.OnClickListener {
        return View.OnClickListener {
            //implement send request
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Member>() {
        override fun areItemsTheSame(oldItem: Member, newItem: Member): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: Member, newItem: Member): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllMemberListViewHolder {
        return AllMemberListViewHolder(
            ItemAllMembersLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: AllMemberListViewHolder, position: Int) {
        val member = getItem(position)
        holder.apply {
            bind(createOnClickListener(member), member)
        }
    }
}