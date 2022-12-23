/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.news

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.devstrike.app.citrarb.features.news.newsLanding.data.local.LocalNewsList

/**
 * Created by Richard Uzor  on 23/12/2022
 */
@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNewsListItem(newsListItem: LocalNewsList)

    //select all notes that have not being locally deleted and sort them by date in descending order
    @Query("SELECT * FROM LocalNewsList WHERE locallyDeleted =0 ORDER BY date DESC")
    fun getAllNewsListOrderByDate(): Flow<List<LocalNewsList>>

    //function to actually delete the local note
    @Query("DELETE FROM LocalNewsList WHERE id=:newsItemId")
    suspend fun deleteNewsListItem(newsItemId: String)

    //we do not really want to delete the note, so we just update the deleted status of the local note to true (1)
    @Query("UPDATE LocalNewsList SET locallyDeleted = 1 WHERE id = :newsItemId")
    suspend fun deleteNewsListItemLocally(newsItemId: String)

    //function to get all locally deleted notes
    @Query("SELECT * FROM LocalNewsList WHERE locallyDeleted = 1")
    suspend fun getAllLocallyDeletedNewsListItem(): List<LocalNewsList>

}