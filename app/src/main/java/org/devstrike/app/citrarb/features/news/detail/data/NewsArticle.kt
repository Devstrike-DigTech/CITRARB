/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.news.detail.data

data class NewsArticle(
    val article: String,
    val coverPhoto: String,
    val images: List<String>?,
    val title: String
)