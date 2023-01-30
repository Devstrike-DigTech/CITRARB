/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.events.data.models.responses

import com.google.gson.annotations.SerializedName

data class CreateEventResponse(
    @SerializedName("data")
    val createdEvent: CreatedEvent,
    val status: String
)