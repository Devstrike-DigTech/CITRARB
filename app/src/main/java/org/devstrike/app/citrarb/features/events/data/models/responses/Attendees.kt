/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.events.data.models.responses

data class Attendees(
    val __v: Int,
    val _id: String,
    val eventId: String,
    val status: String,
    val userId: String
)