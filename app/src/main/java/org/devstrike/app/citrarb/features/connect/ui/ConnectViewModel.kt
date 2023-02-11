/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.connect.ui

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import org.devstrike.app.citrarb.base.BaseViewModel
import org.devstrike.app.citrarb.features.connect.data.models.response.GetOccupationListResponse
import org.devstrike.app.citrarb.features.connect.data.models.requests.CreateOccupationRequest
import org.devstrike.app.citrarb.features.connect.data.models.response.CreateOccupationResponse
import org.devstrike.app.citrarb.features.connect.repositories.ConnectRepoImpl
import org.devstrike.app.citrarb.network.Resource
import javax.inject.Inject

/**
 * Created by Richard Uzor  on 07/02/2023
 */
@HiltViewModel
class ConnectViewModel @Inject constructor(
    private val connectRepo: ConnectRepoImpl
) : BaseViewModel(connectRepo) {

    //state for creating an event
    private val _createOccupationState = MutableSharedFlow<Resource<CreateOccupationResponse>>()
    val createOccupationState: SharedFlow<Resource<CreateOccupationResponse>> =
        _createOccupationState

    //state for creating an event
    private val _getOccupationListState = MutableSharedFlow<Resource<GetOccupationListResponse>>()
    val getOccupationListState: SharedFlow<Resource<GetOccupationListResponse>> =
        _getOccupationListState

    var savedConnectsList = connectRepo.getConnectsListItemFromDB()
    var searchQuery: String = ""


    val getAllConnect = connectRepo.getConnects().asLiveData()

    fun createOccupation(createdOccupation: CreateOccupationRequest) = viewModelScope.launch {
        _createOccupationState.emit(Resource.Loading)
        _createOccupationState.emit(connectRepo.createOccupation(createdOccupation))
    }

    fun getOccupationList() = viewModelScope.launch {
        _getOccupationListState.emit(Resource.Loading)
        _getOccupationListState.emit(connectRepo.getOccupationList())
    }



}