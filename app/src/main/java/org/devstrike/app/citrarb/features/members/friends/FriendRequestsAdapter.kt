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
import org.devstrike.app.citrarb.databinding.ItemAllMembersLayoutBinding
import org.devstrike.app.citrarb.databinding.ItemFriendRequestLayoutBinding
import org.devstrike.app.citrarb.features.members.allmembers.AllMembersAdapter
import org.devstrike.app.citrarb.features.members.data.models.responses.FriendRequest
import org.devstrike.app.citrarb.features.members.data.models.responses.Member
import org.devstrike.app.citrarb.features.members.data.models.responses.Requester
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 * Created by Richard Uzor  on 23/01/2023
 */
/**
 * Created by Richard Uzor  on 23/01/2023
 */
class FriendRequestsAdapter @Inject constructor(
    val context: Context
) : ListAdapter<FriendRequest, FriendRequestsAdapter.FriendRequestsListViewHolder>(DiffCallback()) {


    private var isRequestedAccepted by Delegates.notNull<Boolean>()

    class FriendRequestsListViewHolder(val binding: ItemFriendRequestLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemData: FriendRequest) {
            binding.apply {
                friendRequestItem = itemData
                //itemSendRequestClickListener = listener
                executePendingBindings()
            }
        }
    }



    private class DiffCallback : DiffUtil.ItemCallback<FriendRequest>() {
        override fun areItemsTheSame(oldItem: FriendRequest, newItem: FriendRequest): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: FriendRequest, newItem: FriendRequest): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendRequestsListViewHolder {
        return FriendRequestsListViewHolder(
            ItemFriendRequestLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FriendRequestsListViewHolder, position: Int) {
        val requester = getItem(position)
        holder.apply {
            bind(requester)
        }.also {
            it.binding.ivAcceptFriendRequest.setOnClickListener {
                isRequestedAccepted = true
                onItemAcceptClickListener?.invoke(requester)
            }
            it.binding.ivRejectFriendRequest.setOnClickListener {
                isRequestedAccepted = false
                onItemRejectClickListener?.invoke(requester)

            }

        }
    }
    private var onItemAcceptClickListener: ((FriendRequest) -> Unit)? = null
    private var onItemRejectClickListener: ((FriendRequest) -> Unit)? = null
    fun createOnAcceptClickListener(listener: (FriendRequest) -> Unit){
            onItemAcceptClickListener = listener
    }

    fun createOnRejectClickListener(listener: (FriendRequest) -> Unit){
            onItemRejectClickListener = listener
    }
}