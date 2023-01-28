/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.events.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import org.devstrike.app.citrarb.features.events.concluded.ConcludedEvents
import org.devstrike.app.citrarb.features.events.upcoming.UpcomingEvents
import org.devstrike.app.citrarb.features.members.allmembers.AllMembers
import org.devstrike.app.citrarb.features.members.friends.Friends

/**
 * Created by Richard Uzor  on 28/01/2023
 */
/**
 * Created by Richard Uzor  on 28/01/2023
 */
class EventsLandingPagerAdapter (var context: FragmentActivity?,
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
                UpcomingEvents()
            }
            1 -> {
                ConcludedEvents()
            }
            else -> getItem(position)
        }
    }
}