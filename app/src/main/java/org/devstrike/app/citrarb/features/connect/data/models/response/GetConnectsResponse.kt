/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.connect.data.models.response

data class GetConnectsResponse(
    val `data`: List<Connect>,
    val length: Int,
    val status: String
)