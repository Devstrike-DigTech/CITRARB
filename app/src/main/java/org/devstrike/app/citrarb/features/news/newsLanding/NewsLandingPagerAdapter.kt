/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.news.newsLanding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import org.devstrike.app.citrarb.features.news.ui.categories.all.AllNews
import org.devstrike.app.citrarb.features.news.ui.categories.local.LocalNews
import org.devstrike.app.citrarb.features.news.ui.categories.national.NationalNews

/**
 * adapter class to define the display mode and functionality of the tab layout on the news landing page
 * Created by Richard Uzor  on 20/12/2022
 */
class NewsLandingPagerAdapter(
    var context: FragmentActivity?,
    fm: FragmentManager,
    var totalTabs: Int
) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return totalTabs
    }

    //when each tab is selected, define the fragment to be implemented
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                AllNews()
            }
            1 -> {
                LocalNews()
            }
            2 -> {
                NationalNews()
            }
            else -> getItem(position)
        }
    }

}