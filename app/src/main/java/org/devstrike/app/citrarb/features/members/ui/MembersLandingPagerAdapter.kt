/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.members.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import org.devstrike.app.citrarb.features.members.allmembers.AllMembers
import org.devstrike.app.citrarb.features.members.friends.Friends
import org.devstrike.app.citrarb.features.news.ui.categories.all.AllNews
import org.devstrike.app.citrarb.features.news.ui.categories.local.LocalNews
import org.devstrike.app.citrarb.features.news.ui.categories.national.NationalNews

/**
 * Created by Richard Uzor  on 23/01/2023
 */
/**
 * Created by Richard Uzor  on 23/01/2023
 */
class MembersLandingPagerAdapter (var context: FragmentActivity?,
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
                AllMembers()
            }
            1 -> {
                Friends()
            }
            else -> getItem(position)
        }
    }
}
