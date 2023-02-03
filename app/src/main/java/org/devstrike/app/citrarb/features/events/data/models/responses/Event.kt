/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.events.data.models.responses

import androidx.room.*
import com.google.gson.annotations.SerializedName
import org.devstrike.app.citrarb.utils.CitrarbTypeConverters

@Entity
@TypeConverters(CitrarbTypeConverters::class)
data class Event(
    @SerializedName("_id")
    val uid: String,
    @SerializedName("eventAttendance")
    val attendees: List<String>,
    val coHosts: List<String>,
    val host: String,
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val location: String,
    val name: String,
    val numberOfAttendee: Int,
    val time: String
)