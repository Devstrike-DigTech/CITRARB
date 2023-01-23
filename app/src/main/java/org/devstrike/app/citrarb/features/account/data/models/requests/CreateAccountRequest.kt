/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.account.data.models.requests

data class CreateAccountRequest(
    val confirmPassword: String? = null,
    val email: String,
    val password: String,
    val username: String? = null
)