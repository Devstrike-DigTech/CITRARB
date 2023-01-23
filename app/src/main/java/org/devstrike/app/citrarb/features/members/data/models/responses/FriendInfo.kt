/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.members.data.models.responses

data class FriendInfo(
    val __v: Int,
    val _id: String,
    val active: Boolean,
    val friend: Friend,
    val userId: String
)