/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.connect.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import org.devstrike.app.citrarb.databinding.ItemConnectOccupationLayoutBinding
import org.devstrike.app.citrarb.databinding.ItemUpcomingEventsLayoutBinding
import org.devstrike.app.citrarb.features.connect.data.models.response.Connect
import org.devstrike.app.citrarb.features.events.data.models.responses.Event
import org.devstrike.app.citrarb.utils.Common.userId
import org.devstrike.app.citrarb.utils.convertISODateToMonthYearAndTime
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 * Created by Richard Uzor  on 30/01/2023
 */
/**
 * Created by Richard Uzor  on 30/01/2023
 */
class ConnectAdapter @Inject constructor(
    val context: Context
) : RecyclerView.Adapter<ConnectAdapter.ConnectsListViewHolder>() {

    class ConnectsListViewHolder(val binding: ItemConnectOccupationLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemData: Connect) {
            binding.apply {
                occupationItem = itemData
                //itemSendRequestClickListener = listener
                executePendingBindings()
            }
        }
    }

    //implement the getter and setter for the diff util with the notes
    private val diffUtil = DiffCallback()
    val differ = AsyncListDiffer(this, diffUtil)
    var connects: MutableList<Connect>
        get() = differ.currentList
        set(value) = differ.submitList(value)


    private class DiffCallback : DiffUtil.ItemCallback<Connect>() {
        override fun areItemsTheSame(oldItem: Connect, newItem: Connect): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: Connect, newItem: Connect): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConnectsListViewHolder {
        return ConnectsListViewHolder(
            ItemConnectOccupationLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ConnectsListViewHolder, position: Int) {
        val connect = connects[position]
        holder.apply {
            bind(connect)
        }.also { itemView ->
            itemView.binding.btnMessageConnect.setOnClickListener {
                onItemMessageClickListener?.invoke(connect)
            }
            itemView.binding.btnCallConnect.setOnClickListener {
                onItemCallClickListener?.invoke(connect)
            }
            itemView.binding.btnShareConnect.setOnClickListener {
                onItemShareClickListener?.invoke(connect)
            }
            itemView.binding.root.setOnClickListener {
                onItemClickListener?.invoke(connect)
            }
        }
    }

    private var onItemCallClickListener: ((Connect) -> Unit)? = null
    private var onItemMessageClickListener: ((Connect) -> Unit)? = null
    private var onItemShareClickListener: ((Connect) -> Unit)? = null
    private var onItemClickListener: ((Connect) -> Unit)? = null
    fun createOnCallClickListener(listener: (Connect) -> Unit) {
        onItemCallClickListener = listener
    }

    fun createOnMessageClickListener(listener: (Connect) -> Unit) {
        onItemMessageClickListener = listener
    }

    fun createOnShareClickListener(listener: (Connect) -> Unit) {
        onItemShareClickListener = listener
    }

    fun createOnItemClickListener(listener: (Connect) -> Unit) {
        onItemClickListener = listener
    }

    override fun getItemCount(): Int {
        return connects.size
    }
}