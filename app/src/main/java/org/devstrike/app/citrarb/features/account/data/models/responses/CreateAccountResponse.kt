/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.account.data.models.responses

data class CreateAccountResponse(
    val status: String,
    val token: String,
    val user: User
)