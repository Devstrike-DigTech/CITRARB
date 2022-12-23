/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb

import androidx.room.Database
import androidx.room.RoomDatabase
import org.devstrike.app.citrarb.features.news.newsLanding.data.local.LocalNewsList
import org.devstrike.app.citrarb.features.news.NewsDao

/**
 * Created by Richard Uzor  on 23/12/2022
 */
/**
 * Created by Richard Uzor  on 23/12/2022
 */
@Database(entities = [LocalNewsList::class], version = 1, exportSchema = false)
abstract class CitrarbDatabase: RoomDatabase() {

    abstract fun getNewsListDao(): NewsDao

}