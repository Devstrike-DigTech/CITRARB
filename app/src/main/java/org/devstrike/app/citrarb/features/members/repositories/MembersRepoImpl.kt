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
import org.devstrike.app.citrarb.features.members.data.MembersApi
import org.devstrike.app.citrarb.features.members.data.models.responses.AllUsersResponse
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
    val sessionManager: SessionManager
): MembersRepo, BaseRepo() {

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
}