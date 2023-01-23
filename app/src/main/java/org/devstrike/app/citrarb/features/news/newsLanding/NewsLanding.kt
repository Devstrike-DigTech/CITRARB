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
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import org.devstrike.app.citrarb.R
import org.devstrike.app.citrarb.base.BaseFragment
import org.devstrike.app.citrarb.base.BaseRepo
import org.devstrike.app.citrarb.databinding.FragmentNewsLandingBinding
import org.devstrike.app.citrarb.features.landing.ui.LandingScreen
import org.devstrike.app.citrarb.features.news.data.NewsApi
import org.devstrike.app.citrarb.features.news.data.NewsDao
import org.devstrike.app.citrarb.features.news.repositories.NewsRepoImpl
import org.devstrike.app.citrarb.utils.Common
import org.devstrike.app.citrarb.utils.snackbar
import javax.inject.Inject
import kotlin.properties.Delegates

/*
* UI fragment class to define and display static UI and functionalities for the news list display
* It contains a tab layout to display all categories of news to be displayed
* */
@AndroidEntryPoint
class NewsLanding : BaseFragment<NewsViewModel, FragmentNewsLandingBinding, BaseRepo>() {

    private lateinit var toolBar: Toolbar

    @set:Inject
    var newsApi: NewsApi by Delegates.notNull<NewsApi>()
    @set:Inject
    var newsDao: NewsDao by Delegates.notNull<NewsDao>()

    //    lateinit var customToolbar: Toolbar
//    val landingScreen = LandingScreen()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val landingScreen = LandingScreen()
//        toolBar = landingScreen.mCustomToolBar
//        toolBar.title = "News"
//        toolBar.subtitle = "Get updated on the country events"
        Common.toolBarTitle = "News"
        Common.toolBarSubTitle = "Get updated on the country events"

        with(binding) {
            //set the title to be displayed on each tab
            tabTitle.addTab(tabTitle.newTab().setText("All"))
            tabTitle.addTab(tabTitle.newTab().setText("Local"))
            tabTitle.addTab(tabTitle.newTab().setText("National"))

            tabTitle.tabGravity = TabLayout.GRAVITY_FILL

//            customToolbar = landingScreen.toolBar() as Toolbar
//            customToolbar.title = "News"

            val adapter = childFragmentManager.let {
                NewsLandingPagerAdapter(
                    activity,
                    it,
                    tabTitle.tabCount
                )
            }
            newsLandingViewPager.adapter = adapter
            newsLandingViewPager.addOnPageChangeListener(
                TabLayout.TabLayoutOnPageChangeListener(
                    tabTitle
                )
            )

            //define the functionality of the tab layout
            tabTitle.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    newsLandingViewPager.currentItem = tab.position
                    tabTitle.setSelectedTabIndicatorColor(resources.getColor(R.color.custom_primary))
                    tabTitle.setTabTextColors(
                        Color.BLACK,
                        resources.getColor(R.color.custom_primary)
                    )
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                    tabTitle.setTabTextColors(Color.WHITE, Color.BLACK)
                }

                override fun onTabReselected(tab: TabLayout.Tab) {}
            })

            ivSavedNews.setOnClickListener {
                val navToSavedNews = NewsLandingDirections.actionNewsLandingToSavedNewsList()
                findNavController().navigate(navToSavedNews)
            }

        }


    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentNewsLandingBinding.inflate(inflater, container, false)


    override fun getViewModel() = NewsViewModel::class.java
    override fun getFragmentRepo() = NewsRepoImpl(newsApi, newsDao)


}