/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.members.data.models.responses

import com.google.gson.annotations.SerializedName

data class MyFriendsResponse(
    @SerializedName("data")
    val friends: List<FriendInfo>,
    val length: Int,
    val status: String
)