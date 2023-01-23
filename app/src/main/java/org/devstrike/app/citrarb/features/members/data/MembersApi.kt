/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.members.data

import org.devstrike.app.citrarb.features.members.data.models.responses.AllUsersResponse
import org.devstrike.app.citrarb.utils.Common.USER_BASE_URL
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

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

}