/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.members.data.models.responses

import com.google.gson.annotations.SerializedName

data class GetPendingFriendRequestsResponse(
    @SerializedName("data")
    val request: List<FriendRequest>,
    val status: String
)