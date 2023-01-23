/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.members.data

import org.devstrike.app.citrarb.features.members.data.models.requests.FriendRequestResponseStatus
import org.devstrike.app.citrarb.features.members.data.models.requests.SendFriendRequest
import org.devstrike.app.citrarb.features.members.data.models.responses.AllUsersResponse
import org.devstrike.app.citrarb.features.members.data.models.responses.FriendRequestAcceptedResponse
import org.devstrike.app.citrarb.features.members.data.models.responses.GetPendingFriendRequestsResponse
import org.devstrike.app.citrarb.features.members.data.models.responses.SendFriendRequestResponse
import org.devstrike.app.citrarb.utils.Common.USER_BASE_URL
import retrofit2.http.*

/**
 * Created by Richard Uzor  on 23/01/2023
 */
interface MembersApi {

    //FETCH ALL USERS
    @Headers("Content-Type: application/json")
    @GET("$USER_BASE_URL/api/users")
    suspend fun getAllUsers(
        @Header("Authorization") token: String
    ): AllUsersResponse

    //SEND FRIEND REQUEST
    @Headers("Content-Type: application/json")
    @POST("$USER_BASE_URL/api/friendrequests")
    suspend fun sendFriendRequest(
        @Header("Authorization") token: String,
        @Body userId: SendFriendRequest
    ): SendFriendRequestResponse

    //FETCH FRIEND REQUESTS
    @Headers("Content-Type: application/json")
    @GET("$USER_BASE_URL/api/friendrequests")
    suspend fun fetchPendingFriendRequests(
        @Header("Authorization") token: String
        ): GetPendingFriendRequestsResponse

    //ACCEPT FRIEND REQUEST
    @Headers("Content-Type: application/json")
    @PATCH("$USER_BASE_URL/api/friendrequests/{id}")
    suspend fun acceptFriendRequest(
        @Header("Authorization") token: String,
        @Path("id") requestId: String,
        @Body friendRequestResponseStatus: FriendRequestResponseStatus
    ): FriendRequestAcceptedResponse

}