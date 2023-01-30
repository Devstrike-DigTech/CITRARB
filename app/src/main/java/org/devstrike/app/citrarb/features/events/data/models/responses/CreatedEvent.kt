/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.events.data.models.responses

data class CreatedEvent(
    val __v: Int,
    val _id: String,
    val coHosts: List<Any>,
    val host: String,
    val id: String,
    val location: String,
    val name: String,
    val numberOfAttendee: Int,
    val time: String
)