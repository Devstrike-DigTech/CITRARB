/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.events.data.models.requests

data class CreateEventRequest(
    val coHosts: List<String>,
    val location: String,
    val name: String,
    val time: String
)