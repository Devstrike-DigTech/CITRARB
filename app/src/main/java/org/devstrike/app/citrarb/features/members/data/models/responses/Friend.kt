/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.members.data.models.responses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Friend(
    @PrimaryKey
    val _id: String,
    val photo: String,
    val username: String
)