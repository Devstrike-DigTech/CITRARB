/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.events.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.devstrike.app.citrarb.R
import org.devstrike.app.citrarb.base.BaseFragment
import org.devstrike.app.citrarb.base.CitrarbDatabase
import org.devstrike.app.citrarb.databinding.FragmentEventsLandingBinding
import org.devstrike.app.citrarb.features.events.data.EventsApi
import org.devstrike.app.citrarb.features.events.data.EventsDao
import org.devstrike.app.citrarb.features.events.data.models.responses.Event
import org.devstrike.app.citrarb.features.events.repositories.EventsRepoImpl
import org.devstrike.app.citrarb.features.members.data.FriendsDao
import org.devstrike.app.citrarb.network.Resource
import org.devstrike.app.citrarb.network.handleApiError
import org.devstrike.app.citrarb.utils.Common
import org.devstrike.app.citrarb.utils.SessionManager
import org.devstrike.app.citrarb.utils.toast
import org.devstrike.app.citrarb.utils.visible
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class EventsLanding : BaseFragment<EventsViewModel, FragmentEventsLandingBinding, EventsRepoImpl>() {

    @set:Inject
    var eventsApi: EventsApi by Delegates.notNull()
    @set:Inject
    var sessionManager: SessionManager by Delegates.notNull()
    @set:Inject
    var friendsDao: FriendsDao by Delegates.notNull()
    @set:Inject
    var eventsDao: EventsDao by Delegates.notNull()
    @set:Inject
    var db: CitrarbDatabase by Delegates.notNull()

    private val eventsViewModel: EventsViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            //set the title to be displayed on each tab
            eventsTabTitle.addTab(eventsTabTitle.newTab().setText("Upcoming"))
            eventsTabTitle.addTab(eventsTabTitle.newTab().setText("Concluded"))

            eventsTabTitle.tabGravity = TabLayout.GRAVITY_FILL

//            customToolbar = landingScreen.toolBar() as Toolbar
//            customToolbar.title = "News"

            val adapter = childFragmentManager.let {
                EventsLandingPagerAdapter(
                    activity,
                    it,
                    eventsTabTitle.tabCount
                )
            }
            eventsLandingViewPager.adapter = adapter
            eventsLandingViewPager.addOnPageChangeListener(
                TabLayout.TabLayoutOnPageChangeListener(
                    eventsTabTitle
                )
            )

            //define the functionality of the tab layout
            eventsTabTitle.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    eventsLandingViewPager.currentItem = tab.position
                    eventsTabTitle.setSelectedTabIndicatorColor(resources.getColor(R.color.custom_secondary))
                    eventsTabTitle.setTabTextColors(
                        Color.BLACK,
                        resources.getColor(R.color.custom_secondary)
                    )
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                    eventsTabTitle.setTabTextColors(Color.WHITE, Color.BLACK)
                }

                override fun onTabReselected(tab: TabLayout.Tab) {}
            })

        }
        subscribeToEventsListEvent()

    }

    private fun subscribeToEventsListEvent() {
        eventsViewModel.getAllEvents.observe(viewLifecycleOwner){ result ->
            lifecycleScope.launch{
                    when(result){
                        is Resource.Success ->{
                            val userId = sessionManager.getCurrentUserId()
                            Common.userId = userId!!
                            hideProgressBar()
                            if (result.value!!.isEmpty()) {
                                requireContext().toast("No events!")
                            }
                        }
                        is Resource.Failure ->{
                            hideProgressBar()
                            handleApiError(result.error){subscribeToEventsListEvent()}
                        }
                        is Resource.Loading ->{
                            showProgressBar()
                        }
                    }

            }

        }

    }


    private fun showProgressBar(){
        binding.progressBarEvents.visible(true)
    }

    private fun hideProgressBar(){
        binding.progressBarEvents.visible(false)
    }

    override fun getFragmentRepo() = EventsRepoImpl(eventsApi, db, friendsDao, eventsDao, sessionManager)

    override fun getViewModel() = EventsViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentEventsLandingBinding.inflate(inflater, container, false)

}