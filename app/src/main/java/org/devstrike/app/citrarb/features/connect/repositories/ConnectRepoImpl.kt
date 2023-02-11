/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.connect.repositories

import androidx.room.withTransaction
import kotlinx.coroutines.flow.Flow
import org.devstrike.app.citrarb.base.BaseRepo
import org.devstrike.app.citrarb.base.CitrarbDatabase
import org.devstrike.app.citrarb.features.connect.data.ConnectApi
import org.devstrike.app.citrarb.features.connect.data.ConnectDao
import org.devstrike.app.citrarb.features.connect.data.models.response.GetOccupationListResponse
import org.devstrike.app.citrarb.features.connect.data.models.requests.CreateOccupationRequest
import org.devstrike.app.citrarb.features.connect.data.models.response.Connect
import org.devstrike.app.citrarb.features.connect.data.models.response.CreateOccupationResponse
import org.devstrike.app.citrarb.features.connect.data.models.response.GetConnectsResponse
import org.devstrike.app.citrarb.network.Resource
import org.devstrike.app.citrarb.network.isNetworkConnected
import org.devstrike.app.citrarb.network.networkBoundResource
import org.devstrike.app.citrarb.utils.SessionManager
import javax.inject.Inject

/**
 * Created by Richard Uzor  on 07/02/2023
 */
/**
 * Created by Richard Uzor  on 07/02/2023
 */
class ConnectRepoImpl @Inject constructor(
    private val connectApi: ConnectApi,
    val db: CitrarbDatabase,
    val connectDao: ConnectDao,
    val sessionManager: SessionManager
) : ConnectRepo, BaseRepo() {

    override suspend fun createOccupation(occupationRequest: CreateOccupationRequest): Resource<CreateOccupationResponse> {
        val token = sessionManager.getJwtToken()
        return safeApiCall {
            //check if there is internet connection
            if (!isNetworkConnected(sessionManager.context)) {
                Resource.Failure(value = "No Internet Connection!")
            }
            connectApi.createOccupation("Bearer ".plus(token!!), occupationRequest)
        }
    }

    override suspend fun getOccupationList(): Resource<GetOccupationListResponse> {
        val token = sessionManager.getJwtToken()
        return safeApiCall {
            //check if there is internet connection
            if (!isNetworkConnected(sessionManager.context)) {
                Resource.Failure(value = "No Internet Connection!")
            }
            connectApi.getOccupationList("Bearer ".plus(token!!))
        }
    }

    override fun getConnects()= networkBoundResource(
        query = {
            connectDao.getAllConnectsOrderByJobTitle()
        },
        fetch = {
            val token = sessionManager.getJwtToken()
            val result = connectApi.getConnects("Bearer ".plus(token!!))
            result
        },
        saveFetchResult = { connectResponse ->
            db.withTransaction {
                connectResponse.data.forEach { event ->
                    //eventsDao.deleteAllEvents()
                    connectDao.insertEvent(event)

                }
            }
        }
    )

    override fun getConnectsListItemFromDB(): Flow<List<Connect>> = connectDao.getAllConnectsOrderByJobTitle()

}