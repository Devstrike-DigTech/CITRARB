/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.account.repositories

import org.devstrike.app.citrarb.features.account.data.models.requests.CreateAccountRequest
import org.devstrike.app.citrarb.features.account.data.models.responses.AccountResponse
import org.devstrike.app.citrarb.features.account.data.models.responses.GetSelfResponse
import org.devstrike.app.citrarb.network.Resource

/**
 * Created by Richard Uzor  on 20/01/2023
 */
interface UserRepo {

    suspend fun createUser(user: CreateAccountRequest): Resource<String>
    suspend fun login(user: CreateAccountRequest): Resource<String>
    suspend fun getUserInfo(): Resource<GetSelfResponse>
    suspend fun logout(): Resource<String>


}