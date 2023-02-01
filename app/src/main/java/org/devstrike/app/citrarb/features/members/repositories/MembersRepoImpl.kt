/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.members.repositories

import dagger.hilt.android.scopes.ActivityRetainedScoped
import org.devstrike.app.citrarb.base.BaseRepo
import org.devstrike.app.citrarb.features.members.data.FriendsDao
import org.devstrike.app.citrarb.features.members.data.MembersApi
import org.devstrike.app.citrarb.features.members.data.models.requests.FriendRequestResponseStatus
import org.devstrike.app.citrarb.features.members.data.models.requests.SendFriendRequest
import org.devstrike.app.citrarb.features.members.data.models.responses.*
import org.devstrike.app.citrarb.network.Resource
import org.devstrike.app.citrarb.network.isNetworkConnected
import org.devstrike.app.citrarb.utils.SessionManager
import javax.inject.Inject

/**
 * Created by Richard Uzor  on 23/01/2023
 */

@ActivityRetainedScoped
class MembersRepoImpl @Inject constructor(
    val membersApi: MembersApi,
    val friendsDao: FriendsDao,
    val sessionManager: SessionManager
) : MembersRepo, BaseRepo() {

    override suspend fun getAllUsers(): Resource<AllUsersResponse> {

        val token = sessionManager.getJwtToken()
        return safeApiCall {
            //check if there is internet connection
            if (!isNetworkConnected(sessionManager.context)) {
                Resource.Failure(value = "No Internet Connection!")
            }
            membersApi.getAllUsers("Bearer ".plus(token!!))
        }

    }

    override suspend fun sendFriendRequest(userId: SendFriendRequest): Resource<SendFriendRequestResponse> {
        val token = sessionManager.getJwtToken()
        return safeApiCall {
            //check if there is internet connection
            if (!isNetworkConnected(sessionManager.context)) {
                Resource.Failure(value = "No Internet Connection!")
            }
            membersApi.sendFriendRequest("Bearer ".plus(token!!), userId)
        }
    }

    override suspend fun fetchPendingFriendRequests(): Resource<GetPendingFriendRequestsResponse> {
        val token = sessionManager.getJwtToken()
        return safeApiCall {
            //check if there is internet connection
            if (!isNetworkConnected(sessionManager.context)) {
                Resource.Failure(value = "No Internet Connection!")
            }
            membersApi.fetchPendingFriendRequests("Bearer ".plus(token!!), "pending")
        }
    }

    override suspend fun acceptFriendRequest(
        id: String,
        friendRequestResponseStatus: FriendRequestResponseStatus
    ): Resource<FriendRequestAcceptedResponse> {
        val token = sessionManager.getJwtToken()
        return safeApiCall {
            //check if there is internet connection
            if (!isNetworkConnected(sessionManager.context)) {
                Resource.Failure(value = "No Internet Connection!")
            }
            membersApi.acceptFriendRequest("Bearer ".plus(token!!), id, friendRequestResponseStatus)
        }
    }

    override suspend fun getMyFriends(): Resource<MyFriendsResponse> {

        val token = sessionManager.getJwtToken()
        return safeApiCall {
            //check if there is internet connection
            if (!isNetworkConnected(sessionManager.context)) {
                Resource.Failure(value = "No Internet Connection!")
            }
            val result = membersApi.getMyFriends("Bearer ".plus(token!!))

                result.friends.forEach{ friendInfo ->
                    friendsDao.insertFriend(friendInfo.friend)
                }



            result


        }

    }
}