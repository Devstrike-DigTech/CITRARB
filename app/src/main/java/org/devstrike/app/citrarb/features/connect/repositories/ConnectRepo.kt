/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.connect.repositories

import kotlinx.coroutines.flow.Flow
import org.devstrike.app.citrarb.features.connect.data.models.response.GetOccupationListResponse
import org.devstrike.app.citrarb.features.connect.data.models.requests.CreateOccupationRequest
import org.devstrike.app.citrarb.features.connect.data.models.response.CreateOccupationResponse
import org.devstrike.app.citrarb.features.connect.data.models.response.Connect
import org.devstrike.app.citrarb.features.members.data.models.responses.Friend
import org.devstrike.app.citrarb.network.Resource

/**
 * Created by Richard Uzor  on 07/02/2023
 */
interface ConnectRepo {

    suspend fun createOccupation(occupationRequest: CreateOccupationRequest): Resource<CreateOccupationResponse>
    suspend fun getOccupationList(): Resource<GetOccupationListResponse>
    fun getConnects(): Flow<Resource<List<Connect>>>
    fun getConnectsListItemFromDB(): Flow<List<Connect>>
}