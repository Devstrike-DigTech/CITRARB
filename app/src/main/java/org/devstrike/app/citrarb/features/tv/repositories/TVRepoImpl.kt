/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.tv.repositories

import android.content.Context
import android.util.Log
import dagger.hilt.android.scopes.ActivityRetainedScoped
import org.devstrike.app.citrarb.base.BaseRepo
import org.devstrike.app.citrarb.features.tv.data.api.TVApi
import org.devstrike.app.citrarb.features.tv.data.model.TVListResponse
import org.devstrike.app.citrarb.network.Resource
import org.devstrike.app.citrarb.network.isNetworkConnected
import org.devstrike.app.citrarb.utils.Common.TAG
import javax.inject.Inject

/**
 * Created by Richard Uzor  on 18/01/2023
 */
/**
 * Created by Richard Uzor  on 18/01/2023
 */
@ActivityRetainedScoped
class TVRepoImpl @Inject constructor(
    private val tVApi: TVApi
) : TVRepo, BaseRepo() {

    override suspend fun getTvVideosList(): Resource<TVListResponse> = safeApiCall {
        //val context =
//        if (!isNetworkConnected(context)){
//                Log.d(TAG, "getNewsListFromServer: No Internet")
//            }
        tVApi.getTvVideosList()
    }

    }