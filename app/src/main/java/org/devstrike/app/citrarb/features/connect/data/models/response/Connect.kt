/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.connect.data.models.response

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Connect(
    @PrimaryKey(autoGenerate = false)
    val _id: String,
    val category: String,
    val description: String,
    val jobTitle: String,
    val name: String,
    val phone: String,
    val userId: String
)