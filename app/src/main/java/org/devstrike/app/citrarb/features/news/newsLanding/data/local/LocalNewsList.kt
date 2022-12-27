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
import org.devstrike.app.citrarb.features.news.detail.data.NewsArticle
import org.devstrike.app.citrarb.features.news.newsLanding.data.remote.NewsListResponse

/**
 * Created by Richard Uzor  on 23/12/2022
 */
@Entity
data class LocalNewsList(
    var newsListInfo: NewsListResponse,
    var newsArticle: NewsArticle,
    var savedDate: String,
    @PrimaryKey (autoGenerate = false)
    var uid: String,
    var isSaved: Boolean,
    var locallyDeleted: Int
)
