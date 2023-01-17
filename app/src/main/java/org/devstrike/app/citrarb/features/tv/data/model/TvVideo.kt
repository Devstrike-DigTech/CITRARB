/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.tv.data.model

data class TvVideo(
    val Link: String,
    val description: String,
    val publishedAt: String,
    val thumbnails: Thumbnails,
    val title: String
)