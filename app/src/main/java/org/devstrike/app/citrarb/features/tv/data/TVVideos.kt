/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.tv.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Richard Uzor  on 18/01/2023
 */
@Parcelize
data class TVVideos(
    val videoTitle: String = "",
    val videoDescription: String = "",
    val videoLink: String = "",
    val videoPublishedDate: String = ""
): Parcelable
