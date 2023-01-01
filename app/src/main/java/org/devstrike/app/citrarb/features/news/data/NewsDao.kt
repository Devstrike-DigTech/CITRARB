/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.news.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.devstrike.app.citrarb.features.news.newsLanding.data.local.SavedNewsListData

/**
 * Data Object Class for the accessibility function of the news table in the database
 * Created by Richard Uzor  on 23/12/2022
 */
@Dao
interface NewsDao {

    //insert a new news item and replace if already exists
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewsListItem(newsListItem: SavedNewsListData)

    //select all news that have not being locally deleted and sort them by date in descending order
    @Query("SELECT * FROM SavedNewsListData WHERE locallyDeleted = 0 ORDER BY savedDate DESC")
    fun getAllNewsListOrderByDate(): Flow<List<SavedNewsListData>>

    //function to actually delete the local news
    @Query("DELETE FROM SavedNewsListData WHERE uid=:newsItemId")
    suspend fun deleteNewsListItem(newsItemId: String)

    //we do not really want to delete the news, so we just update the deleted status of the local news to true (1)
    @Query("UPDATE SavedNewsListData SET locallyDeleted = 1 WHERE uid = :newsItemId")
    suspend fun deleteNewsListItemLocally(newsItemId: String)

    //function to get all locally deleted news
    @Query("SELECT * FROM SavedNewsListData WHERE locallyDeleted = 1")
    suspend fun getAllLocallyDeletedNewsListItem(): List<SavedNewsListData>

}