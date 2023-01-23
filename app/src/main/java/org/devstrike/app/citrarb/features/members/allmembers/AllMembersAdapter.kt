/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.members.allmembers

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.devstrike.app.citrarb.databinding.ItemAllMembersLayoutBinding
import org.devstrike.app.citrarb.features.members.data.models.responses.Member
import javax.inject.Inject

/**
 * Created by Richard Uzor  on 23/01/2023
 */

class AllMembersAdapter @Inject constructor(
    val context: Context
) : ListAdapter<Member, AllMembersAdapter.AllMemberListViewHolder>(DiffCallback()) {


    class AllMemberListViewHolder(val binding: ItemAllMembersLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemData: Member) {
            binding.apply {
                allMembersItem = itemData
                //itemSendRequestClickListener = listener
                executePendingBindings()
            }
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
            bind(member)
        }.binding.btnSendFriendRequest.setOnClickListener {
            onItemClickListener?.invoke(member)
        }
    }
    private var onItemClickListener: ((Member) -> Unit)? = null
    fun createOnClickListener(listener: (Member) -> Unit){
        onItemClickListener = listener
    }
}