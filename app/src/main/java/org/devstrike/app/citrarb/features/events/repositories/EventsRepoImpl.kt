/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.events.repositories

import androidx.room.withTransaction
import kotlinx.coroutines.flow.Flow
import org.devstrike.app.citrarb.base.BaseRepo
import org.devstrike.app.citrarb.base.CitrarbDatabase
import org.devstrike.app.citrarb.features.events.data.EventsApi
import org.devstrike.app.citrarb.features.events.data.EventsDao
import org.devstrike.app.citrarb.features.events.data.models.requests.CreateEventRequest
import org.devstrike.app.citrarb.features.events.data.models.requests.EventAttendanceRequest
import org.devstrike.app.citrarb.features.events.data.models.responses.CreateEventResponse
import org.devstrike.app.citrarb.features.events.data.models.responses.Event
import org.devstrike.app.citrarb.features.events.data.models.responses.EventAttendanceResponse
import org.devstrike.app.citrarb.features.events.data.models.responses.GetEventsResponse
import org.devstrike.app.citrarb.features.members.data.FriendsDao
import org.devstrike.app.citrarb.features.members.data.models.responses.Friend
import org.devstrike.app.citrarb.network.Resource
import org.devstrike.app.citrarb.network.isNetworkConnected
import org.devstrike.app.citrarb.network.networkBoundResource
import org.devstrike.app.citrarb.utils.SessionManager
import javax.inject.Inject

/**
 * Created by Richard Uzor  on 28/01/2023
 */
/**
 * Created by Richard Uzor  on 28/01/2023
 */
class EventsRepoImpl @Inject constructor(
    val eventsApi: EventsApi,
    val db: CitrarbDatabase,
    val friendsDao: FriendsDao,
    val eventsDao: EventsDao,
    val sessionManager: SessionManager
) : EventsRepo, BaseRepo() {

//    : Resource<Flow<Resource<List<Event>>>> {
//
//        return safeApiCall {
//
//
//
//            val fetch
//    //override suspend
    override fun getAllEvents() = networkBoundResource(
                query = {
                    eventsDao.getAllEventsOrderByTime()
                },
                fetch = {
                    val token = sessionManager.getJwtToken()
                    val result = eventsApi.fetchAllEvent("Bearer ".plus(token!!))
                    result
                },
                saveFetchResult = { eventResponse ->
                    db.withTransaction {
                        eventResponse.event.forEach { event ->
                            //eventsDao.deleteAllEvents()
                            eventsDao.insertEvent(event)

                        }
                    }
                }
                    )
//                    //check if there is internet connection
//                    if (!isNetworkConnected(sessionManager.context)) {
//                        Resource.Failure(value = "No Internet Connection!")
//                    }

//            fetch
//        }

    //}

    override suspend fun createEvent(createdEvent: CreateEventRequest): Resource<CreateEventResponse> {
        val token = sessionManager.getJwtToken()
        return safeApiCall {
            //check if there is internet connection
            if (!isNetworkConnected(sessionManager.context)) {
                Resource.Failure(value = "No Internet Connection!")
            }
            eventsApi.createEvent("Bearer ".plus(token!!), createdEvent)
        }
    }

    override suspend fun eventAttendance(attendanceRequest: EventAttendanceRequest): Resource<EventAttendanceResponse> {
        val token = sessionManager.getJwtToken()
        return safeApiCall {
            //check if there is internet connection
            if (!isNetworkConnected(sessionManager.context)) {
                Resource.Failure(value = "No Internet Connection!")
            }
            eventsApi.eventAttendance("Bearer ".plus(token!!), attendanceRequest)
        }
    }


    override fun getFriendsListItemFromDB(): Flow<List<Friend>> =
        friendsDao.getAllFriendsOrderByName()

    override fun getEventsListItemFromDB(): Flow<List<Event>> =
        eventsDao.getAllEventsOrderByTime()



}