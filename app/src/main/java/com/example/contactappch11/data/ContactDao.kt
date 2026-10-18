package com.example.contactappch11.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

// @Dao marks this interface as a Data Access Object
// Room reads this interface at compile time and generates
// the full SQL implementation automatically — you never write SQL manually
@Dao
interface ContactDao {
    // Retrieves all contacts ordered alphabetically by name
    // @Query lets you write custom SQL when the standard annotations are not enough
    // "SELECT * FROM contacts" means get every column from the contacts table
    // "ORDER BY name ASC" means alphabetical order A to Z
    // Flow emits a new list automatically every time any contact changes
    // No suspend needed — Flow manages its own background threading
    @Query("SELECT * FROM contacts ORDER BY name ASC")
    fun getContacts(): Flow<List<Contact>>

    // Inserts a new contact into the database
    // @Insert tells Room to generate a SQL INSERT statement
    // OnConflictStrategy.REPLACE means if a contact with the same ID exists
    // Room deletes the old row and inserts the new one
    // suspend runs this disk write operation off the main thread
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: Contact)

    // Updates an existing contact in the database
    // @Update tells Room to generate a SQL UPDATE statement
    // Room matches the contact by its ID and updates all other fields
    // suspend runs this disk write operation off the main thread
    @Update
    suspend fun updateContact(contact: Contact)

    // Deletes a contact from the database
    // @Delete tells Room to generate a SQL DELETE statement
    // Room matches the contact by its ID and removes the entire row
    // suspend runs this disk write operation off the main thread
    @Delete
    suspend fun deleteContact(contact: Contact)

}