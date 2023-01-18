/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.tv.data.model

data class Thumbnails(
    val default: Default? = Default(),
    val high: High? = High(),
    val maxres: Maxres = Maxres(),
    val medium: Medium = Medium(),
    val standard: Standard = Standard()
)