/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.base

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.devstrike.app.citrarb.features.news.newsLanding.data.local.SavedNewsListData
import org.devstrike.app.citrarb.features.news.data.NewsDao
import org.devstrike.app.citrarb.utils.CitrarbTypeConverters

/**
 * Created by Richard Uzor  on 23/12/2022
 */
/**
 * Created by Richard Uzor  on 23/12/2022
 */
@Database(entities = [SavedNewsListData::class], version = 1, exportSchema = false)
@TypeConverters(CitrarbTypeConverters::class)
abstract class CitrarbDatabase: RoomDatabase() {

    abstract fun getNewsListDao(): NewsDao

}