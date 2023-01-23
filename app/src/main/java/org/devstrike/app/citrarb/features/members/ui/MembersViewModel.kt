/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.members.ui

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import org.devstrike.app.citrarb.base.BaseViewModel
import org.devstrike.app.citrarb.features.account.data.models.responses.GetSelfResponse
import org.devstrike.app.citrarb.features.members.data.models.requests.SendFriendRequest
import org.devstrike.app.citrarb.features.members.data.models.responses.AllUsersResponse
import org.devstrike.app.citrarb.features.members.data.models.responses.SendFriendRequestResponse
import org.devstrike.app.citrarb.features.members.repositories.MembersRepo
import org.devstrike.app.citrarb.features.members.repositories.MembersRepoImpl
import org.devstrike.app.citrarb.network.Resource
import javax.inject.Inject

/**
 * Created by Richard Uzor  on 23/01/2023
 */
/**
 * Created by Richard Uzor  on 23/01/2023
 */
@HiltViewModel
class MembersViewModel @Inject constructor(
    val membersRepo: MembersRepoImpl
): BaseViewModel(membersRepo) {

    //state for all users  fetch
    private val _allUsersState = MutableSharedFlow<Resource<AllUsersResponse>>()
    val allUsersState: SharedFlow<Resource<AllUsersResponse>> = _allUsersState

    //state for send friend request
    private val _sendFriendRequestState = MutableSharedFlow<Resource<SendFriendRequestResponse>>()
    val sendFriendRequestState: SharedFlow<Resource<SendFriendRequestResponse>> = _sendFriendRequestState

    fun getAllUsers() = viewModelScope.launch {
        _allUsersState.emit(Resource.Loading)
        _allUsersState.emit(membersRepo.getAllUsers())
    }

    fun sendFriendRequest(userId: SendFriendRequest) = viewModelScope.launch{
        _sendFriendRequestState.emit(Resource.Loading)
        _sendFriendRequestState.emit(membersRepo.sendFriendRequest(userId))
    }

}