/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

<<<<<<< Updated upstream:app/src/main/java/org/devstrike/app/citrarb/ui/features/news/NewsLandingPagerAdapter.kt
package org.devstrike.app.citrarb.ui.features.news
=======
package org.devstrike.app.citrarb.features.news.newsLanding.ui
>>>>>>> Stashed changes:app/src/main/java/org/devstrike/app/citrarb/ui/features/news/ui/NewsLandingPagerAdapter.kt

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import org.devstrike.app.citrarb.ui.features.news.all.AllNews
import org.devstrike.app.citrarb.ui.features.news.entertainment.EntertainmentNews
import org.devstrike.app.citrarb.ui.features.news.hot.HotNews
import org.devstrike.app.citrarb.ui.features.news.politics.PoliticsNews
import org.devstrike.app.citrarb.ui.features.news.sports.SportsNews

/**
 * Created by Richard Uzor  on 20/12/2022
 */
/**
 * Created by Richard Uzor  on 20/12/2022
 */
class NewsLandingPagerAdapter(var context: FragmentActivity?,
                              fm: FragmentManager,
                              var totalTabs: Int
) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return totalTabs
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                HotNews()
            }
            1 -> {
                AllNews()
            }
            2 -> {
                PoliticsNews()
            }
            3 -> {
                EntertainmentNews()
            }
            4 -> {
                SportsNews()
            }
            else -> getItem(position)
        }
    }

}