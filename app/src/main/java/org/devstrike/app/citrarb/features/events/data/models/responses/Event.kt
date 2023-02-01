/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.events.data.models.responses

import com.google.gson.annotations.SerializedName

data class Event(
    val _id: String,
    @SerializedName("eventAttendance")
    val attendees: List<Attendees>,
    val coHosts: List<String>,
    val host: String,
    val id: String,
    val location: String,
    val name: String,
    val numberOfAttendee: Int,
    val time: String
)