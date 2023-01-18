/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.tv.data.api

import org.devstrike.app.citrarb.features.tv.data.model.TVListResponse
import retrofit2.Response
import retrofit2.http.GET

interface TVApi {


    @GET("api/tv/")
    suspend fun getTvVideosList(): TVListResponse
}