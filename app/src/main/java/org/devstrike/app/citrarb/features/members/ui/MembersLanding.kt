/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.members.ui

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import org.devstrike.app.citrarb.R
import org.devstrike.app.citrarb.base.BaseFragment
import org.devstrike.app.citrarb.base.BaseRepo
import org.devstrike.app.citrarb.base.BaseViewModel
import org.devstrike.app.citrarb.databinding.FragmentMembersLandingBinding
import org.devstrike.app.citrarb.features.news.newsLanding.NewsLandingDirections
import org.devstrike.app.citrarb.features.news.newsLanding.NewsLandingPagerAdapter

class MembersLanding : BaseFragment<BaseViewModel, FragmentMembersLandingBinding, BaseRepo>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        with(binding) {
            //set the title to be displayed on each tab
            membersTabTitle.addTab(membersTabTitle.newTab().setText("Friends"))
            membersTabTitle.addTab(membersTabTitle.newTab().setText("All Members"))

            membersTabTitle.tabGravity = com.google.android.material.tabs.TabLayout.GRAVITY_FILL

//            customToolbar = landingScreen.toolBar() as Toolbar
//            customToolbar.title = "News"

            val adapter = childFragmentManager.let {
                MembersLandingPagerAdapter(
                    activity,
                    it,
                    membersTabTitle.tabCount
                )
            }
            membersLandingViewPager.adapter = adapter
            membersLandingViewPager.addOnPageChangeListener(
                com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener(
                    membersTabTitle
                )
            )

            //define the functionality of the tab layout
            membersTabTitle.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    membersLandingViewPager.currentItem = tab.position
                    membersTabTitle.setSelectedTabIndicatorColor(resources.getColor(org.devstrike.app.citrarb.R.color.custom_primary))
                    membersTabTitle.setTabTextColors(
                        android.graphics.Color.BLACK,
                        resources.getColor(org.devstrike.app.citrarb.R.color.custom_primary)
                    )
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                    membersTabTitle.setTabTextColors(android.graphics.Color.WHITE, android.graphics.Color.BLACK)
                }

                override fun onTabReselected(tab: TabLayout.Tab) {}
            })

//            ivSavedNews.setOnClickListener {
//                val navToSavedNews = org.devstrike.app.citrarb.features.news.newsLanding.NewsLandingDirections.actionNewsLandingToSavedNewsList()
//                findNavController().navigate(navToSavedNews)
//            }

        }


    }

    override fun getFragmentRepo(): BaseRepo {
        TODO("Not yet implemented")
    }

    override fun getViewModel(): Class<BaseViewModel> {
        TODO("Not yet implemented")
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMembersLandingBinding.inflate(inflater, container, false)

}