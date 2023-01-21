/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.account.data

import org.devstrike.app.citrarb.features.account.data.models.requests.CreateAccountRequest
import org.devstrike.app.citrarb.features.account.data.models.responses.CreateAccountResponse
import org.devstrike.app.citrarb.utils.Common.USER_BASE_URL
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * Created by Richard Uzor  on 20/01/2023
 */
interface UserApi {

    //CREATE USER
    @Headers("Content-Type: application/json")
    @POST("$USER_BASE_URL/api/users/signup")
    suspend fun createAccount(
        @Body user: CreateAccountRequest
    ): CreateAccountResponse

    //LOGIN USER
    @Headers("Content-Type: application/json")
    @POST("$USER_BASE_URL/api/users/login")
    suspend fun login(
        @Body user: CreateAccountRequest
    ): CreateAccountResponse

}