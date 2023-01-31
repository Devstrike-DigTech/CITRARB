/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.events.ui

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import org.devstrike.app.citrarb.base.BaseViewModel
import org.devstrike.app.citrarb.features.events.data.models.requests.CreateEventRequest
import org.devstrike.app.citrarb.features.events.data.models.requests.EventAttendanceRequest
import org.devstrike.app.citrarb.features.events.data.models.responses.CreateEventResponse
import org.devstrike.app.citrarb.features.events.data.models.responses.EventAttendanceResponse
import org.devstrike.app.citrarb.features.events.data.models.responses.GetEventsResponse
import org.devstrike.app.citrarb.features.events.repositories.EventsRepo
import org.devstrike.app.citrarb.features.events.repositories.EventsRepoImpl
import org.devstrike.app.citrarb.features.members.data.models.requests.SendFriendRequest
import org.devstrike.app.citrarb.features.members.data.models.responses.AllUsersResponse
import org.devstrike.app.citrarb.features.members.data.models.responses.SendFriendRequestResponse
import org.devstrike.app.citrarb.features.members.repositories.MembersRepoImpl
import org.devstrike.app.citrarb.network.Resource
import javax.inject.Inject

/**
 * Created by Richard Uzor  on 28/01/2023
 */
/**
 * Created by Richard Uzor  on 28/01/2023
 */
@HiltViewModel
class EventsViewModel @Inject constructor(
    val eventsRepo: EventsRepoImpl
) : BaseViewModel(eventsRepo) {

    //state for all events  fetch
    private val _allEventsState = MutableSharedFlow<Resource<GetEventsResponse>>()
    val allEventsState: SharedFlow<Resource<GetEventsResponse>> = _allEventsState

    //state for creating an event
    private val _createEventState = MutableSharedFlow<Resource<CreateEventResponse>>()
    val createEventState: SharedFlow<Resource<CreateEventResponse>> =
        _createEventState

 //state for event attendance request
    private val _eventAttendanceState = MutableSharedFlow<Resource<EventAttendanceResponse>>()
    val eventAttendanceState: SharedFlow<Resource<EventAttendanceResponse>> =
        _eventAttendanceState



    fun getAllEvents() = viewModelScope.launch {
        _allEventsState.emit(Resource.Loading)
        _allEventsState.emit(eventsRepo.getAllEvents())
    }

    fun createEvent(createdEvent: CreateEventRequest) = viewModelScope.launch {
        _createEventState.emit(Resource.Loading)
        _createEventState.emit(eventsRepo.createEvent(createdEvent))
    }

    fun eventAttendance(attendanceRequest: EventAttendanceRequest) = viewModelScope.launch {
        _eventAttendanceState.emit(Resource.Loading)
        _eventAttendanceState.emit(eventsRepo.eventAttendance(attendanceRequest))
    }

}