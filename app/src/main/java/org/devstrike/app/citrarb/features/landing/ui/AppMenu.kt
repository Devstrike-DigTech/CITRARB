/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.landing.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import org.devstrike.app.citrarb.base.BaseFragment
import org.devstrike.app.citrarb.features.landing.data.LandingMenu
import org.devstrike.app.citrarb.databinding.FragmentAppMenuBinding
import org.devstrike.app.citrarb.features.landing.adapter.LandingMenuAdapter
import org.devstrike.app.citrarb.utils.Common

class AppMenu : BaseFragment<FragmentAppMenuBinding>() {


    private val landingMenuAdapter = LandingMenuAdapter()
    val landingMenuList = mutableListOf<LandingMenu>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()

    }

    //function to set up views and logic for the recycler view UI
    private fun setUpRecyclerView() {
        binding.landingMenuGrid.apply {
            adapter = landingMenuAdapter
            layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)

        }
        landingMenuAdapter.submitList(Common.landingMenuList)


    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAppMenuBinding.inflate(inflater, container, false)


}