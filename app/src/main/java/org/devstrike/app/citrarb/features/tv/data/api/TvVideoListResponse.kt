/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.tv.data.api

import org.devstrike.app.citrarb.features.tv.data.model.TvVideo

data class TvVideoListResponse(
    val data: List<TvVideo>
)