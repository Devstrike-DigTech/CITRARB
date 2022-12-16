/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.ui.features.landing

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.qualifiers.ApplicationContext
import org.devstrike.app.citrarb.R
import org.devstrike.app.citrarb.data.LandingMenu
import org.devstrike.app.citrarb.databinding.ItemLandingGridLayoutBinding
import org.devstrike.app.citrarb.utils.Common.TAG

/**
 * Created by Richard Uzor  on 15/12/2022
 */
class LandingMenuAdapter: ListAdapter<LandingMenu, LandingMenuAdapter.LandingMenuViewHolder>(DiffCallback()) {

    class LandingMenuViewHolder(private val binding: ItemLandingGridLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(itemData: LandingMenu, listener: View.OnClickListener){
            binding.apply {
                landingMenu = itemData
                menuClick = listener
                executePendingBindings()
            }
        }
    }

    private class DiffCallback: DiffUtil.ItemCallback<LandingMenu>(){
        override fun areItemsTheSame(oldItem: LandingMenu, newItem: LandingMenu): Boolean {
            return oldItem.itemName == newItem.itemName
        }

        override fun areContentsTheSame(
            oldItem: LandingMenu,
            newItem: LandingMenu
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LandingMenuViewHolder {
        Log.d(TAG, "onCreateViewHolder: adapter create")
        return LandingMenuViewHolder(ItemLandingGridLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: LandingMenuViewHolder, position: Int) {
        val menuItem = getItem(position)
        holder.apply {
            bind(menuItem, createOnClickListener(menuItem))
            itemView.tag = menuItem
            itemView.setBackgroundColor(Color.parseColor(menuItem.itemColor))
            if (menuItem.itemName == "News")
            itemView.setBackgroundColor(R.drawable.app_background)
        }
    }
    private fun createOnClickListener(menuItem: LandingMenu): View.OnClickListener {
        return View.OnClickListener {
            //navigate to page to show doctor details using navigation directions
            Log.d(TAG, "createOnClickListener: ${menuItem.itemName}")
//            if (menuItem.itemName == "News"){
//                //val direction = PersonnelFragmentDirections.actionPersonnelFragmentToPractitionerItem2(pharmacist.id)
//                //it.findNavController().navigate(direction)
//                Log.d(TAG, "createOnClickListener: ${menuItem.itemName}")
//            }

        }
    }



}