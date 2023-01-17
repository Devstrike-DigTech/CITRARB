/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.tv.repository

import org.devstrike.app.citrarb.features.tv.data.api.TvVideoRetrofitInstance

class TvVideoRepo {
    suspend fun getTvVideos() = TvVideoRetrofitInstance.api.getTvVideos()
}