/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.members.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.devstrike.app.citrarb.features.members.data.models.responses.Friend

/**
 * Created by Richard Uzor  on 01/02/2023
 */
@Dao
interface FriendsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFriend(friend: Friend)

    //select all friends that have not being locally deleted and sort them by date in descending order
    @Query("SELECT * FROM Friend ORDER BY username DESC")
    fun getAllFriendsOrderByName(): Flow<List<Friend>>

    //function to actually delete the local friend
    @Query("DELETE FROM Friend WHERE _id=:friendId")
    suspend fun deleteFriend(friendId: String)

//    //we do not really want to delete the note, so we just update the deleted status of the local note to true (1)
//    @Query("UPDATE Friend SET locallyDeleted = 1 WHERE noteId = :noteId")
//    suspend fun deleteNoteLocally(noteId: String)
//
//    //function to get all the notes that are not on the server
//    @Query("SELECT * FROM LocalNote WHERE connected = 0")
//    suspend fun getAllLocalNotes(): List<LocalNote>

}