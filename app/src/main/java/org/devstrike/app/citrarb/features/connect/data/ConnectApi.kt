/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.connect.data

import org.devstrike.app.citrarb.features.connect.data.models.response.GetOccupationListResponse
import org.devstrike.app.citrarb.features.connect.data.models.requests.CreateOccupationRequest
import org.devstrike.app.citrarb.features.connect.data.models.response.CreateOccupationResponse
import org.devstrike.app.citrarb.features.connect.data.models.response.GetConnectsResponse
import org.devstrike.app.citrarb.utils.Common
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * Created by Richard Uzor  on 07/02/2023
 */
interface ConnectApi {

    //CREATE OCCUPATION
    @Headers("Content-Type: application/json")
    @POST("${Common.USER_BASE_URL}/api/occupations")
    suspend fun createOccupation(
        @Header("Authorization") token: String,
        @Body occupation: CreateOccupationRequest
    ): CreateOccupationResponse


    //GET OCCUPATION LIST
    @Headers("Content-Type: application/json")
    @GET("${Common.USER_BASE_URL}/api/occupations/list")
    suspend fun getOccupationList(
        @Header("Authorization") token: String,
    ): GetOccupationListResponse


    //GET OCCUPATION LIST
    @Headers("Content-Type: application/json")
    @GET("${Common.USER_BASE_URL}/api/occupations")
    suspend fun getConnects(
        @Header("Authorization") token: String,
    ): GetConnectsResponse



}