/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.ui.features.landing

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import org.devstrike.app.citrarb.R
import org.devstrike.app.citrarb.base.BaseFragment
import org.devstrike.app.citrarb.data.LandingMenu
import org.devstrike.app.citrarb.databinding.FragmentLandingScreenBinding
import org.devstrike.app.citrarb.utils.Common
import org.devstrike.app.citrarb.utils.toast

class LandingScreen : BaseFragment<FragmentLandingScreenBinding>() {

    private val landingMenuAdapter = LandingMenuAdapter()
    val landingMenuList = mutableListOf<LandingMenu>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(binding.customToolBar)

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.tool_bar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)


        val settingsItem = menu.findItem(R.id.menu_settings)
        val accountsItem = menu.findItem(R.id.menu_account)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_account -> {
                requireContext().toast("Account Clicked!")
                //findNavController().navigate(R.id.action_allNotes_to_userInfo)
            }
            R.id.menu_settings-> {
                requireContext().toast("Settings Clicked!")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentLandingScreenBinding.inflate(layoutInflater, container, false)

}