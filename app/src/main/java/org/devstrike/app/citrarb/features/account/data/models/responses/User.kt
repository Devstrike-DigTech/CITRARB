/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.account.data.models.responses

data class User(
    val __v: Int,
    val _id: String,
    val active: Boolean?,
    val createdAt: String,
    val email: String,
    val photo: String,
    val role: String,
    val updatedAt: String,
    val username: String
)