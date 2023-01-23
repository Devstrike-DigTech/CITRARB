/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.members.data.models.responses

data class FriendRequest(
    val __v: Int,
    val _id: String,
    val requester: Requester,
    val status: String,
    val userId: String
)