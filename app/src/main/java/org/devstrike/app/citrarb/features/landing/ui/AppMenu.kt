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
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.devstrike.app.citrarb.base.BaseFragment
import org.devstrike.app.citrarb.features.landing.data.LandingMenu
import org.devstrike.app.citrarb.databinding.FragmentAppMenuBinding
import org.devstrike.app.citrarb.features.landing.adapter.LandingMenuAdapter
import org.devstrike.app.citrarb.features.landing.repositories.LandingRepo
import org.devstrike.app.citrarb.features.news.newsLanding.data.remote.NewsListResponse
import org.devstrike.app.citrarb.utils.Common
import org.devstrike.app.citrarb.utils.Common.deepLinkNewsUrl

/*
* UI Fragment to display the app menu on the landing page
* */

@AndroidEntryPoint
class AppMenu : BaseFragment<LandingViewModel, FragmentAppMenuBinding, LandingRepo>() {

    lateinit var toolBar: Toolbar


    private val landingMenuAdapter = LandingMenuAdapter()
    val landingMenuList = mutableListOf<LandingMenu>()
    private var emptyNewsList = NewsListResponse()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val landingScreen = LandingScreen()
//        toolBar = landingScreen.mCustomToolBar
        //toolBar.title = "News"
        //when an external link is clicked to open in the app, it gets the link and navigates to the news
        if (deepLinkNewsUrl != "") {
            val navToNewsDetail =
                AppMenuDirections.actionAppMenuToNewsDetail(deepLinkNewsUrl, "", emptyNewsList)
            findNavController().navigate(navToNewsDetail)
        }
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

    override fun getFragmentRepo() = LandingRepo()

    override fun getViewModel() = LandingViewModel::class.java


}