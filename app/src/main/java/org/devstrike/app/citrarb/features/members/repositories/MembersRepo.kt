/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.members.repositories

import org.devstrike.app.citrarb.features.members.data.models.requests.FriendRequestResponseStatus
import org.devstrike.app.citrarb.features.members.data.models.requests.SendFriendRequest
import org.devstrike.app.citrarb.features.members.data.models.responses.*
import org.devstrike.app.citrarb.network.Resource

/**
 * Created by Richard Uzor  on 23/01/2023
 */
interface MembersRepo {

    suspend fun getAllUsers(): Resource<AllUsersResponse>
    suspend fun sendFriendRequest(userId: SendFriendRequest): Resource<SendFriendRequestResponse>
    suspend fun fetchPendingFriendRequests(): Resource<GetPendingFriendRequestsResponse>
    suspend fun acceptFriendRequest(id: String, friendRequestResponseStatus: FriendRequestResponseStatus): Resource<FriendRequestAcceptedResponse>
    suspend fun getMyFriends(): Resource<MyFriendsResponse>

}