/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.news.newsLanding

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import org.devstrike.app.citrarb.R
import org.devstrike.app.citrarb.base.BaseFragment
import org.devstrike.app.citrarb.databinding.FragmentNewsLandingBinding
import org.devstrike.app.citrarb.utils.snackbar

class NewsLanding : BaseFragment<FragmentNewsLandingBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            tabTitle.addTab(tabTitle.newTab().setText("Hot"))
            tabTitle.addTab(tabTitle.newTab().setText("All"))
            tabTitle.addTab(tabTitle.newTab().setText("Politics"))
            tabTitle.addTab(tabTitle.newTab().setText("Sports"))
            tabTitle.addTab(tabTitle.newTab().setText("Entertainment"))

            tabTitle.tabGravity = TabLayout.GRAVITY_FILL

            val adapter = childFragmentManager.let {
                NewsLandingPagerAdapter(
                    activity,
                    it,
                    tabTitle.tabCount
                )
            }
            newsLandingViewPager.adapter = adapter
            newsLandingViewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabTitle))

            tabTitle.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    newsLandingViewPager.currentItem = tab.position
                    tabTitle.setSelectedTabIndicatorColor(resources.getColor(R.color.custom_primary))
                    tabTitle.setTabTextColors(Color.BLACK, resources.getColor(R.color.custom_primary))
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                    tabTitle.setTabTextColors(Color.WHITE, Color.BLACK)
                }

                override fun onTabReselected(tab: TabLayout.Tab) {}
            })

            ivSavedNews.setOnClickListener {
                requireView().snackbar("Saved News Coming Soon...")
            }

        }


    }


override fun getFragmentBinding(
    inflater: LayoutInflater,
    container: ViewGroup?
) = FragmentNewsLandingBinding.inflate(inflater, container, false)


}