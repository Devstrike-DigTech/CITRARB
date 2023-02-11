/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.events.upcoming

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.devstrike.app.citrarb.databinding.ItemUpcomingEventsLayoutBinding
import org.devstrike.app.citrarb.features.events.data.models.responses.Event
import org.devstrike.app.citrarb.features.members.data.models.responses.Friend
import org.devstrike.app.citrarb.features.members.data.models.responses.FriendRequest
import org.devstrike.app.citrarb.utils.Common.userId
import org.devstrike.app.citrarb.utils.SessionManager
import org.devstrike.app.citrarb.utils.convertISODateToMonthYearAndTime
import org.devstrike.app.citrarb.utils.loadImage
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 * Created by Richard Uzor  on 30/01/2023
 */
/**
 * Created by Richard Uzor  on 30/01/2023
 */
class UpcomingEventsAdapter @Inject constructor(
    val context: Context
) : RecyclerView.Adapter<UpcomingEventsAdapter.UpcomingEventsListViewHolder>() {

    private var isInviteAccepted by Delegates.notNull<Boolean>()

    class UpcomingEventsListViewHolder(val binding: ItemUpcomingEventsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemData: Event) {
            binding.apply {
                upcomingEventItem = itemData
                //itemSendRequestClickListener = listener
                executePendingBindings()
            }
        }
    }

    //implement the getter and setter for the diff util with the notes
    private val diffUtil = DiffCallback()
    val differ = AsyncListDiffer(this, diffUtil)
    var events: MutableList<Event>
        get() = differ.currentList
        set(value) = differ.submitList(value)



    private class DiffCallback : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.uid == newItem.uid
        }

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingEventsListViewHolder {
        return UpcomingEventsListViewHolder(
            ItemUpcomingEventsLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: UpcomingEventsListViewHolder, position: Int) {
        val event = events[position]
        holder.apply {
            bind(event)
        }.also { itemView ->
            itemView.binding.btnUpcomingEventGoing.setOnClickListener {
                if (itemView.binding.txtAttendanceStatus.text == "Not Going"){
                    isInviteAccepted = true
                    onItemAcceptClickListener?.invoke(event)
                }else{
                    isInviteAccepted = false
                    onItemRejectClickListener?.invoke(event)
                }

            }
//            itemView.binding.btnUpcomingEventNotGoing.setOnClickListener {
//                isInviteAccepted = false
//                onItemRejectClickListener?.invoke(event)
//            }
            itemView.binding.txtEventDate.text = convertISODateToMonthYearAndTime(event.time)
            val eventHosts = mutableListOf<String>()
            eventHosts.add(event.host)
            for (host in event.coHosts){
                eventHosts.add(host)
            }
            for (host in eventHosts)
                itemView.binding.txtEventHosts.append("$host, ")
            //itemView.binding.backgroundImage.loadImage()

            itemView.binding.txtAttendanceStatus.apply {
                this.text = if(event.attendees.contains(userId)){
                     "Going"
                }else{
                    "Not Going"
                }
            }
        }
    }
    private var onItemAcceptClickListener: ((Event) -> Unit)? = null
    private var onItemRejectClickListener: ((Event) -> Unit)? = null
    fun createOnAcceptClickListener(listener: (Event) -> Unit){
        onItemAcceptClickListener = listener
    }

    fun createOnRejectClickListener(listener: (Event) -> Unit){
        onItemRejectClickListener = listener
    }

    override fun getItemCount(): Int {
        return events.size
    }
}