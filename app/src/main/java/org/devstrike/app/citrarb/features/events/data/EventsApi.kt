/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.events.data

import org.devstrike.app.citrarb.features.events.data.models.requests.CreateEventRequest
import org.devstrike.app.citrarb.features.events.data.models.responses.CreateEventResponse
import org.devstrike.app.citrarb.features.events.data.models.responses.GetEventsResponse
import org.devstrike.app.citrarb.features.members.data.models.requests.SendFriendRequest
import org.devstrike.app.citrarb.features.members.data.models.responses.SendFriendRequestResponse
import org.devstrike.app.citrarb.utils.Common
import retrofit2.http.*

/**
 * Created by Richard Uzor  on 28/01/2023
 */
interface EventsApi {

    @Headers("Content-Type: application/json")
    @POST("${Common.USER_BASE_URL}/api/events")
    suspend fun createEvent(
        @Header("Authorization") token: String,
        @Body createdEvent: CreateEventRequest
    ): CreateEventResponse

    @Headers("Content-Type: application/json")
    @GET("${Common.USER_BASE_URL}/api/events")
    suspend fun fetchAllEvent(
        @Header("Authorization") token: String
    ): GetEventsResponse



}