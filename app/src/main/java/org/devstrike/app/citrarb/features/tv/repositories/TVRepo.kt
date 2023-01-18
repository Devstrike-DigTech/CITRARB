/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.tv.repositories

import org.devstrike.app.citrarb.features.tv.data.model.TVListResponse
import org.devstrike.app.citrarb.network.Resource

interface TVRepo {
    suspend fun getTvVideosList(): Resource<TVListResponse>

}