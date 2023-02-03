/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.events.concluded

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.devstrike.app.citrarb.base.BaseFragment
import org.devstrike.app.citrarb.base.CitrarbDatabase
import org.devstrike.app.citrarb.databinding.FragmentConcludedEventsBinding
import org.devstrike.app.citrarb.features.events.data.EventsApi
import org.devstrike.app.citrarb.features.events.data.EventsDao
import org.devstrike.app.citrarb.features.events.data.models.responses.Event
import org.devstrike.app.citrarb.features.events.repositories.EventsRepoImpl
import org.devstrike.app.citrarb.features.events.ui.EventsViewModel
import org.devstrike.app.citrarb.features.members.data.FriendsDao
import org.devstrike.app.citrarb.utils.SessionManager
import org.devstrike.app.citrarb.utils.convertISODateToMillis
import org.devstrike.app.citrarb.utils.toast
import org.devstrike.app.citrarb.utils.visible
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class ConcludedEvents : BaseFragment<EventsViewModel, FragmentConcludedEventsBinding, EventsRepoImpl>() {


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


    val eventsViewModel: EventsViewModel by activityViewModels()

    private lateinit var concludedEventsAdapter: ConcludedEventsAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        concludedEventsAdapter = ConcludedEventsAdapter(requireContext())
        subscribeToConcludedEventsListEvent()

        binding.ivSearchConcludedEvents.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    searchConcludedEvent(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    searchConcludedEvent(it)
                }
                return true
            }

        })


    }

    //function to actually search the notes with the entered search characters that match saved notes
    private fun searchConcludedEvent(query: String) = lifecycleScope.launch {
        eventsViewModel.searchQuery = query
        concludedEventsAdapter.events = eventsViewModel.savedEventsList.first().filter {
            it.name.contains(query, true) || it.location.contains(query, true) || it.host.contains(
                query,
                true
            )
        }.toMutableList()
    }



    private fun subscribeToConcludedEventsListEvent() {
        lifecycleScope.launch{
            //val userId = sessionManager.getCurrentUserId()
            val query = eventsViewModel.searchQuery
            eventsViewModel.savedEventsList.collect{ result ->
                if (result.isEmpty()) {
                    requireContext().toast("No concluded events!")
                } else {
                    val concludedEvents = mutableListOf<Event>()
                    for (event in result) {
                        val eventTime = convertISODateToMillis(event.time)
                        if (eventTime < System.currentTimeMillis()) { //is concluded
                            concludedEvents.add(event)
                        }
                    }
                    //concludedEventsAdapter.submitList(concludedEvents)
                    concludedEventsAdapter.events = concludedEvents.filter { event ->
                        event.name.contains(query, true) || event.location.contains(query, true) || event.host.contains(
                            query,
                            true
                        )
                    }.toMutableList()
                    subscribeToConcludedEventsUi()
                }
            }
        }

    }

    private fun subscribeToConcludedEventsUi() {
        binding.rvConcludedEvents.visible(true)

        val upcomingEventsListLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.rvConcludedEvents.apply {
            adapter = concludedEventsAdapter
            layoutManager = upcomingEventsListLayoutManager
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(), upcomingEventsListLayoutManager.orientation
                )
            )
        }

        concludedEventsAdapter.createOnItemClickListener { event ->

        }


    }


    override fun getFragmentRepo() = EventsRepoImpl(eventsApi, db, friendsDao, eventsDao, sessionManager)

    override fun getViewModel() = EventsViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentConcludedEventsBinding.inflate(inflater, container, false)

}