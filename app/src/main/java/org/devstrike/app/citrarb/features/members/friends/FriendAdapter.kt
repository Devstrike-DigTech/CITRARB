/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.members.friends

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.devstrike.app.citrarb.databinding.ItemFriendRequestLayoutBinding
import org.devstrike.app.citrarb.databinding.ItemFriendsLayoutBinding
import org.devstrike.app.citrarb.features.members.data.models.responses.FriendInfo
import org.devstrike.app.citrarb.features.members.data.models.responses.FriendRequest
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 * Created by Richard Uzor  on 23/01/2023
 */
/**
 * Created by Richard Uzor  on 23/01/2023
 */
class FriendAdapter @Inject constructor(
    val context: Context
) : ListAdapter<FriendInfo, FriendAdapter.FriendListViewHolder>(DiffCallback()) {


    private var isRequestedAccepted by Delegates.notNull<Boolean>()

    class FriendListViewHolder(val binding: ItemFriendsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemData: FriendInfo) {
            binding.apply {
                friendItem = itemData
                //itemSendRequestClickListener = listener
                executePendingBindings()
            }
        }
    }



    private class DiffCallback : DiffUtil.ItemCallback<FriendInfo>() {
        override fun areItemsTheSame(oldItem: FriendInfo, newItem: FriendInfo): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: FriendInfo, newItem: FriendInfo): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendListViewHolder {
        return FriendListViewHolder(
            ItemFriendsLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FriendListViewHolder, position: Int) {
        val requester = getItem(position)
        holder.apply {
            bind(requester)
        }.also {
            it.binding.btnCallFriend.setOnClickListener {
                isRequestedAccepted = true
                onItemCallClickListener?.invoke(requester)
            }
            it.binding.btnMessageFriend.setOnClickListener {
                isRequestedAccepted = false
                onItemMessageClickListener?.invoke(requester)

            }

        }
    }
    private var onItemCallClickListener: ((FriendInfo) -> Unit)? = null
    private var onItemMessageClickListener: ((FriendInfo) -> Unit)? = null
    fun createOnCallClickListener(listener: (FriendInfo) -> Unit){
        onItemCallClickListener = listener
    }

    fun createOnMessageClickListener(listener: (FriendInfo) -> Unit){
        onItemMessageClickListener = listener
    }
}