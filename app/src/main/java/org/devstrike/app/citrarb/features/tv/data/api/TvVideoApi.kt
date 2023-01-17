/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.tv.data.api

import org.devstrike.app.citrarb.features.tv.data.api.TvVideoListResponse
import retrofit2.Response
import retrofit2.http.GET

interface TvVideoApi {
    @GET("api/tv/")
    suspend fun getTvVideos(): Response<TvVideoListResponse>
}