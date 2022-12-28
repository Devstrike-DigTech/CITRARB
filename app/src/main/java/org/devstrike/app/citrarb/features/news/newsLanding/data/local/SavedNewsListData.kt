/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.news.newsLanding.data.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import org.devstrike.app.citrarb.features.news.detail.data.NewsArticle
import org.devstrike.app.citrarb.features.news.newsLanding.data.remote.NewsListResponse
import java.util.*
import kotlin.random.Random

/**
 * Created by Richard Uzor  on 23/12/2022
 */
@Entity
@Parcelize
data class SavedNewsListData(
    val author: String?,
    val category: String?,
    val date: String?,
    val description: String?,
    val title: String?,
    val article: String?,
    val link: String?,
    val coverPhoto: String?,
    var savedDate: String?,
    @PrimaryKey (autoGenerate = false)
    var uid: String = UUID.randomUUID().toString(),
    var isSaved: Boolean,
    var locallyDeleted: Int
): Parcelable
