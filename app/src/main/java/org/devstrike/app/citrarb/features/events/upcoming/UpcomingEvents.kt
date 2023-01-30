/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.events.upcoming

import android.media.metrics.Event
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.devstrike.app.citrarb.R
import org.devstrike.app.citrarb.base.BaseFragment
import org.devstrike.app.citrarb.databinding.FragmentUpcomingEventsBinding
import org.devstrike.app.citrarb.features.events.data.EventsApi
import org.devstrike.app.citrarb.features.events.repositories.EventsRepoImpl
import org.devstrike.app.citrarb.features.events.ui.EventsViewModel
import org.devstrike.app.citrarb.network.Resource
import org.devstrike.app.citrarb.network.handleApiError
import org.devstrike.app.citrarb.utils.SessionManager
import org.devstrike.app.citrarb.utils.toast
import org.devstrike.app.citrarb.utils.visible
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class UpcomingEvents : BaseFragment<EventsViewModel, FragmentUpcomingEventsBinding, EventsRepoImpl>() {

    @set:Inject
    var eventsApi: EventsApi by Delegates.notNull()
    @set:Inject
    var sessionManager: SessionManager by Delegates.notNull()

    val eventsViewModel: EventsViewModel by activityViewModels()
    private lateinit var upcomingEventsAdapter: UpcomingEventsAdapter

    val TAG = "UpcomingEvents"


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        upcomingEventsAdapter = UpcomingEventsAdapter(requireContext())

        subscribeToUpcomingEventsListEvent()

    }

    private fun subscribeToUpcomingEventsListEvent() {
        eventsViewModel.getAllEvents()
        lifecycleScope.launch{
            eventsViewModel.allEventsState.collect{ result ->
                when(result){
                    is Resource.Success ->{
                        binding.upcomingEventShimmerLayout.apply {
                            stopShimmer()
                            visible(false)
                        }
                        if (result.value!!.event.isEmpty())
                            requireContext().toast("No upcoming events!")
                        else
                            upcomingEventsAdapter.submitList(result.value.event)
                        subscribeToUpcomingEventsUi()
                    }
                    is Resource.Failure ->{
                        binding.upcomingEventShimmerLayout.stopShimmer()
                        binding.upcomingEventShimmerLayout.visible(false)
                        handleApiError(result.error){subscribeToUpcomingEventsListEvent()}
                    }
                    is Resource.Loading ->{
                        binding.upcomingEventShimmerLayout.startShimmer()
                    }
                }
            }
        }

    }

    private fun subscribeToUpcomingEventsUi() {
        binding.rvUpcomingEvents.visible(true)

        val upcomingEventsListLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.rvUpcomingEvents.apply {
            adapter = upcomingEventsAdapter
            layoutManager = upcomingEventsListLayoutManager
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(), upcomingEventsListLayoutManager.orientation
                )
            )
        }

        upcomingEventsAdapter.createOnAcceptClickListener { event ->
            requireContext().toast("Going to ${event.id}")
        }
        upcomingEventsAdapter.createOnRejectClickListener { event ->
            requireContext().toast("Not going to ${event.id}")
        }


    }

    override fun getFragmentRepo() = EventsRepoImpl(eventsApi, sessionManager)

    override fun getViewModel() = EventsViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentUpcomingEventsBinding.inflate(inflater, container, false)

}