/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.events.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.devstrike.app.citrarb.features.events.data.models.responses.Event
import org.devstrike.app.citrarb.features.members.data.models.responses.Friend

/**
 * Created by Richard Uzor  on 02/02/2023
 */
@Dao
interface EventsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: Event)

    //select all events that have not being locally deleted and sort them by date in descending order
    @Query("SELECT * FROM Event ORDER BY time DESC")
    fun getAllEventsOrderByTime(): Flow<List<Event>>

    //function to actually delete the local event
    @Query("DELETE FROM Event WHERE uid=:eventId")
    suspend fun deleteEvent(eventId: String)

  //function to delete all the local event
    @Query("DELETE FROM Event")
    suspend fun deleteAllEvents()

}