/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.events.upcoming

import android.content.ContentValues
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.devstrike.app.citrarb.R
import org.devstrike.app.citrarb.base.BaseFragment
import org.devstrike.app.citrarb.databinding.FragmentUpcomingEventsBinding
import org.devstrike.app.citrarb.features.events.data.EventsApi
import org.devstrike.app.citrarb.features.events.data.models.requests.EventAttendanceRequest
import org.devstrike.app.citrarb.features.events.data.models.responses.Data
import org.devstrike.app.citrarb.features.events.data.models.responses.Event
import org.devstrike.app.citrarb.features.events.repositories.EventsRepoImpl
import org.devstrike.app.citrarb.features.events.ui.EventsLandingDirections
import org.devstrike.app.citrarb.features.events.ui.EventsViewModel
import org.devstrike.app.citrarb.network.Resource
import org.devstrike.app.citrarb.network.handleApiError
import org.devstrike.app.citrarb.utils.SessionManager
import org.devstrike.app.citrarb.utils.getYearFromISODate
import org.devstrike.app.citrarb.utils.toast
import org.devstrike.app.citrarb.utils.visible
import java.util.*
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

        binding.fabAddEvent.setOnClickListener {
            val popup = PopupMenu(requireContext(), it)
            popup.menuInflater.inflate(R.menu.events_menu, popup.menu)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.event_menu_new_event -> {
                        val navToCreateNewEvent = EventsLandingDirections.actionEventsLandingToCreateEvent()
                        findNavController().navigate(navToCreateNewEvent)
                        true
                    }
                    R.id.event_menu_attending_event -> {
                        // Perform action for option 2
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }

        subscribeToUpcomingEventsListEvent()

    }



    private fun subscribeToUpcomingEventsListEvent() {
        eventsViewModel.getAllEvents()
        lifecycleScope.launch{
            eventsViewModel.allEventsState.collect{ result ->
                when(result){
                    is Resource.Success ->{
                        val userId = sessionManager.getCurrentUserId()
                        binding.upcomingEventShimmerLayout.apply {
                            stopShimmer()
                            visible(false)
                        }
                        if (result.value!!.event.isEmpty()) {
                            requireContext().toast("No upcoming events!")
                        } else {
                            val unansweredEvents = mutableListOf<Event>()
                            for (event in result.value.event){
                                for (attendee in event.attendees){
                                    if (attendee.userId != userId){
                                        unansweredEvents.add(event)
                                        Log.d(TAG, "subscribeToUpcomingEventsListEvent inside attendees loop: $unansweredEvents")
                                    }
                                }
                                Log.d(TAG, "subscribeToUpcomingEventsListEvent inside events loop: $unansweredEvents")
                            }
                            Log.d(TAG, "subscribeToUpcomingEventsListEvent outside all loops: $unansweredEvents")
                            upcomingEventsAdapter.submitList(unansweredEvents)
                            subscribeToUpcomingEventsUi()
                        }
                    }
                    is Resource.Failure ->{
                        binding.upcomingEventShimmerLayout.stopShimmer()
                        binding.upcomingEventShimmerLayout.visible(false)
                        handleApiError(result.error){subscribeToUpcomingEventsListEvent()}
                    }
                    is Resource.Loading ->{
                        binding.upcomingEventShimmerLayout.visible(true)
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
            val attendanceRequest = EventAttendanceRequest(
                eventId = event.id,
                status = "going"
            )

            makeReservation(attendanceRequest, event)
        }
        upcomingEventsAdapter.createOnRejectClickListener { event ->
            requireContext().toast("Not going to ${event.id}")
        }


    }

    private fun makeReservation(
        attendanceRequest: EventAttendanceRequest,
        event: Event
    ) {

        eventsViewModel.eventAttendance(attendanceRequest)
        lifecycleScope.launch{
            eventsViewModel.eventAttendanceState.collect{ result ->
                when(result){
                    is Resource.Success ->{
                        addToCalendar(result.value!!.data, event)
                        hideProgressBar()
                        requireContext().toast("Added to calendar!")

                    }
                    is Resource.Failure ->{
                        hideProgressBar()
                        handleApiError(result.error){makeReservation(attendanceRequest, event)}
                    }
                    is Resource.Loading ->{
                        showProgressBar()
                    }
                }
            }
        }

    }

    private fun addToCalendar(data: Data, event: Event) {
        val eventHosts = mutableListOf<String>()
        eventHosts.add(event.host)
        for (host in event.coHosts)
            eventHosts.add(host)

        val hosts = ""
        for (host in eventHosts)
            hosts.plus(host).plus(", ")

        val contentResolver = requireContext().contentResolver

        val calendarDetails = getYearFromISODate(event.time)
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, calendarDetails.year)
        calendar.set(Calendar.MONTH, calendarDetails.month)
        calendar.set(Calendar.DAY_OF_MONTH, calendarDetails.dayOfMonth)
        calendar.set(Calendar.HOUR_OF_DAY, calendarDetails.hour)
        calendar.set(Calendar.MINUTE, calendarDetails.minute)
        calendar.set(Calendar.SECOND, calendarDetails.second)

        val values = ContentValues().apply {
            put(CalendarContract.Events.DTSTART, calendar.timeInMillis)
            put(CalendarContract.Events.DTEND, calendar.timeInMillis + 60 * 60 * 1000)
            put(CalendarContract.Events.TITLE, event.name)
            put(CalendarContract.Events.DESCRIPTION, "Hosts: $hosts")
            put(CalendarContract.Events.CALENDAR_ID, 1)
            put(CalendarContract.Events.EVENT_TIMEZONE, calendar.timeZone.id)
            put(CalendarContract.Events.EVENT_LOCATION, event.location)
            put(CalendarContract.Events.HAS_ATTENDEE_DATA, "${event.numberOfAttendee} Going")

        }

        contentResolver.insert(CalendarContract.Events.CONTENT_URI, values)



    }


    private fun showProgressBar(){
        binding.upcomingEventsProgressBar.visible(true)
    }

    private fun hideProgressBar(){
        binding.upcomingEventsProgressBar.visible(false)
    }

    override fun getFragmentRepo() = EventsRepoImpl(eventsApi, sessionManager)

    override fun getViewModel() = EventsViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentUpcomingEventsBinding.inflate(inflater, container, false)

}