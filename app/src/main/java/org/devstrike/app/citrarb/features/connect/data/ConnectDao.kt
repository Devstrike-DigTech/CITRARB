/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.connect.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.devstrike.app.citrarb.features.connect.data.models.response.Connect

/**
 * Created by Richard Uzor  on 07/02/2023
 */
@Dao
interface ConnectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(connect: Connect)

    //select all events that have not being locally deleted and sort them by date in descending order
    @Query("SELECT * FROM Connect ORDER BY jobTitle DESC")
    fun getAllConnectsOrderByJobTitle(): Flow<List<Connect>>

    //function to actually delete the local event
    @Query("DELETE FROM Connect WHERE _id=:connectId")
    suspend fun deleteConnect(connectId: String)

    //function to delete all the local event
    @Query("DELETE FROM Connect")
    suspend fun deleteAllConnects()

}