/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.events.newevent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.devstrike.app.citrarb.databinding.ItemEventCoHostsListLayoutBinding
import org.devstrike.app.citrarb.databinding.ItemFriendsHostListLayoutBinding
import org.devstrike.app.citrarb.features.events.upcoming.UpcomingEventsAdapter
import org.devstrike.app.citrarb.features.members.data.models.responses.Friend

/**
 * Created by Richard Uzor  on 01/02/2023
 */
/**
 * Created by Richard Uzor  on 01/02/2023
 */
class EventCoHostsAdapter :
    ListAdapter<Friend, EventCoHostsAdapter.EventCoHostsViewHolder>(DiffCallback()) {

    class EventCoHostsViewHolder(val binding: ItemEventCoHostsListLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    //implement diff util object to perform list similarity checks and updates
    private class DiffCallback : DiffUtil.ItemCallback<Friend>() {
        override fun areItemsTheSame(oldItem: Friend, newItem: Friend): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: Friend, newItem: Friend): Boolean {
            return oldItem == newItem
        }

    }
//
//    //implement the getter and setter for the diff util with the notes
//    val differ = AsyncListDiffer(this, diffUtil)
//    var friends: List<Friend>
//        get() = differ.currentList
//        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventCoHostsViewHolder {
        return EventCoHostsAdapter.EventCoHostsViewHolder(
            ItemEventCoHostsListLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: EventCoHostsViewHolder, position: Int) {
        val friend = getItem(position)

        holder.binding.apply {
            friend.username.let {
                txtCoHost.text = it
            }

            viewRemoveCoHost.setOnClickListener {
                onRemoveHostClickListener?.invoke(friend)

            }

        }

    }

    //create click listener for the note items to always be passed as a parameter of the event
    private var onRemoveHostClickListener: ((Friend) -> Unit)? = null

    fun setOnRemoveHostClickListener(listener: (Friend) -> Unit) {
        onRemoveHostClickListener = listener
    }
//
//    override fun getItemCount(): Int {
//        return friends.size
//    }


}