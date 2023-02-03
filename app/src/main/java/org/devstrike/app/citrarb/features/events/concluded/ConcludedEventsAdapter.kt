/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.events.concluded

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.devstrike.app.citrarb.databinding.ItemConcludedEventsLayoutBinding
import org.devstrike.app.citrarb.features.events.data.models.responses.Event
import org.devstrike.app.citrarb.features.events.upcoming.UpcomingEventsAdapter
import org.devstrike.app.citrarb.utils.Common
import org.devstrike.app.citrarb.utils.convertISODateToMonthYearAndTime
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 * Created by Richard Uzor  on 02/02/2023
 */
/**
 * Created by Richard Uzor  on 02/02/2023
 */
class ConcludedEventsAdapter @Inject constructor(
    val context: Context
) : RecyclerView.Adapter<ConcludedEventsAdapter.ConcludedEventsListViewHolder>() {

    private var isInviteAccepted by Delegates.notNull<Boolean>()

    class ConcludedEventsListViewHolder(val binding: ItemConcludedEventsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemData: Event) {
            binding.apply {
                concludedEventItem = itemData
                //concludedEventItemClicker = listener
                executePendingBindings()
            }
        }
    }


    private class DiffCallback : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.uid == newItem.uid
        }

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem == newItem
        }

    }


    //implement the getter and setter for the diff util with the notes
    private val diffUtil = ConcludedEventsAdapter.DiffCallback()
    val differ = AsyncListDiffer(this, diffUtil)
    var events: MutableList<Event>
        get() = differ.currentList
        set(value) = differ.submitList(value)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ConcludedEventsListViewHolder {
        return ConcludedEventsListViewHolder(
            ItemConcludedEventsLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ConcludedEventsListViewHolder, position: Int) {
        val event = events[position]
        holder.apply {
            bind(event)
        }.also { itemView ->
            itemView.binding.root.setOnClickListener {
                onItemClickListener?.invoke(event)
            }
//            itemView.binding.btnUpcomingEventNotGoing.setOnClickListener {
//                isInviteAccepted = false
//                onItemRejectClickListener?.invoke(event)
//            }
            itemView.binding.txtEventDate.text = convertISODateToMonthYearAndTime(event.time)
            val eventHosts = mutableListOf<String>()
            eventHosts.add(event.host)
            for (host in event.coHosts) {
                eventHosts.add(host)
            }
            for (host in eventHosts)
                itemView.binding.txtEventHosts.append("$host, ")
            //itemView.binding.backgroundImage.loadImage()
            itemView.binding.txtEventAttendance.apply {
                this.text = if(event.attendees.contains(Common.userId)){
                    "Attended"
                }else{
                    "Not Attended"
                }
            }
        }
    }

    private var onItemClickListener: ((Event) -> Unit)? = null
    fun createOnItemClickListener(listener: (Event) -> Unit) {
        onItemClickListener = listener
    }

    override fun getItemCount(): Int {
        return events.size
    }

}