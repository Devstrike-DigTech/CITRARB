/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.landing.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import org.devstrike.app.citrarb.R
import org.devstrike.app.citrarb.base.BaseFragment
import org.devstrike.app.citrarb.databinding.FragmentLandingScreenBinding
import org.devstrike.app.citrarb.features.landing.repositories.LandingRepo
import org.devstrike.app.citrarb.utils.toast

class LandingScreen : BaseFragment<LandingViewModel, FragmentLandingScreenBinding, LandingRepo>() {

//    private lateinit var screenBinding: FragmentLandingScreenBinding
//    lateinit var mCustomToolBar: Toolbar
//
//    fun toolBar(): View{
//        return mCustomToolBar
//    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        mCustomToolBar = screenBinding.customToolBar
        (activity as AppCompatActivity).setSupportActionBar(binding.customToolBar)

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

    override fun getFragmentRepo() = LandingRepo()

    override fun getViewModel() = LandingViewModel::class.java


}