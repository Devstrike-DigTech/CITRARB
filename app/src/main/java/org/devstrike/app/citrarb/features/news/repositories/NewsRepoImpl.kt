/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.news.repositories

import org.devstrike.app.citrarb.features.news.NewsApi
import org.devstrike.app.citrarb.features.news.NewsDao

/**
 * Created by Richard Uzor  on 23/12/2022
 */
/**
 * Created by Richard Uzor  on 23/12/2022
 */
class NewsRepoImpl(
    private val newsApi: NewsApi,
    val newsDao: NewsDao
): NewsRepo {
}