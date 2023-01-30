/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.events.repositories

import org.devstrike.app.citrarb.features.events.data.models.requests.CreateEventRequest
import org.devstrike.app.citrarb.features.events.data.models.responses.CreateEventResponse
import org.devstrike.app.citrarb.features.events.data.models.responses.GetEventsResponse
import org.devstrike.app.citrarb.features.members.data.models.requests.SendFriendRequest
import org.devstrike.app.citrarb.features.members.data.models.responses.AllUsersResponse
import org.devstrike.app.citrarb.features.members.data.models.responses.SendFriendRequestResponse
import org.devstrike.app.citrarb.network.Resource

/**
 * Created by Richard Uzor  on 28/01/2023
 */
interface EventsRepo {

    suspend fun getAllEvents(): Resource<GetEventsResponse>
    suspend fun createEvent(createdEvent: CreateEventRequest): Resource<CreateEventResponse>
}