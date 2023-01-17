/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.landing.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.devstrike.app.citrarb.R
import org.devstrike.app.citrarb.features.landing.data.LandingMenu
import org.devstrike.app.citrarb.databinding.ItemLandingGridLayoutBinding
import org.devstrike.app.citrarb.features.landing.ui.AppMenuDirections
import org.devstrike.app.citrarb.features.landing.ui.LandingScreen
import org.devstrike.app.citrarb.utils.Common.TAG
import org.devstrike.app.citrarb.utils.snackbar

/**
 * Adapter class to populate the recyclerview for the landing page app menu
 * Created by Richard Uzor  on 15/12/2022
 */
class LandingMenuAdapter : ListAdapter<LandingMenu, LandingMenuAdapter.LandingMenuViewHolder>(
    DiffCallback()
) {

    val landingScreen = LandingScreen()

    class LandingMenuViewHolder(private val binding: ItemLandingGridLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: View.OnClickListener, itemData: LandingMenu) {
            binding.apply {
                landingMenu = itemData
                menuClick = listener
                executePendingBindings()
            }
        }
    }

    //internal class to check for duplicate items and objects
    private class DiffCallback : DiffUtil.ItemCallback<LandingMenu>() {
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
        return LandingMenuViewHolder(
            ItemLandingGridLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: LandingMenuViewHolder, position: Int) {
        val menuItem = getItem(position)
        holder.apply {
            bind(createOnClickListener(menuItem), menuItem)
            itemView.tag = menuItem
            itemView.setBackgroundColor(Color.parseColor(menuItem.itemColor))
            if (menuItem.itemName == "News")
                itemView.setBackgroundColor(R.drawable.app_background)
        }
    }

    //function to handle the click of the various items
    private fun createOnClickListener(menuItem: LandingMenu): View.OnClickListener {
        return View.OnClickListener {
            //navigate to page to show doctor details using navigation directions
            Log.d(TAG, "createOnClickListener: ${menuItem.itemName}")
            when (menuItem.itemName) {
                "News" -> {
                    val navToNews = AppMenuDirections.actionAppMenuToNewsLanding()
                    it.findNavController().navigate(navToNews)
                }
                "Eye Witness" -> {
                    it.snackbar("${menuItem.itemName} coming soon...")
                }
                "TV" -> {
                    //it.snackbar("${menuItem.itemName} coming soon...")
                    val navToTv = AppMenuDirections.actionAppMenuToTVVideoList()
                    it.findNavController().navigate(navToTv)
                }
                "Events" -> {
                    it.snackbar("${menuItem.itemName} coming soon...")
                }
                "Market Place" -> {
                    it.snackbar("${menuItem.itemName} coming soon...")
                }
                "Music" -> {
                    it.snackbar("${menuItem.itemName} coming soon...")
                }
                "Connect" -> {
                    it.snackbar("${menuItem.itemName} coming soon...")
                }
                "Members" -> {
                    it.snackbar("${menuItem.itemName} coming soon...")
                }
                "Uploads" -> {
                    it.snackbar("${menuItem.itemName} coming soon...")
                }
            }
//            if (menuItem.itemName == "News"){
//                //val direction = PersonnelFragmentDirections.actionPersonnelFragmentToPractitionerItem2(pharmacist.id)
//                //it.findNavController().navigate(direction)
//                Log.d(TAG, "createOnClickListener: ${menuItem.itemName}")
//            }

        }
    }


}