/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.news.newsLanding.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Richard Uzor  on 23/12/2022
 */
@Entity
data class LocalNewsList(
    val author: String,
    val category: String,
    val cover_photo_big_size: String,
    val cover_photo_small_size: String,
    val date: String,
    val description: String,
    @PrimaryKey
    val id: String,
    val link: String,
    val title: String,
    val isSaved: Boolean,
    val locallyDeleted: Int
)
