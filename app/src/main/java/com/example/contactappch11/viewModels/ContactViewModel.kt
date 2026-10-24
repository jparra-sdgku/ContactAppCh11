package com.example.contactappch11.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactappch11.data.Contact
import com.example.contactappch11.data.ContactDatabase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ContactViewModel (application: Application): AndroidViewModel(application){

    // Get the DAO from the database singleton
    // This is the single entry  point of access to the database operations
    // The DAO was created in the ContactDatabase.getDatabase class
    // So calling it again returns the same instance of the DAO
    private val _dao = ContactDatabase.getDatabase(application).contactDao()
    // Exposes the list of contacts as a StateFlow
    // StateFlow is a hot flow that emits the current and new values
    //state.in lets the flow start emitting values
    // scope = viewModelScope: the Flow lives as long as the ViewModel
    val contacts : StateFlow<List<Contact>> = _dao.getContacts().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Save a new contact to the database
    // viewModelScope.launch starts a coroutine in the background
    // The suspend function insertContact() pauses and resumes without freezing the UI
    // The user can still scroll, tap buttons, or type while this completes
    fun saveContact(contact: Contact){
        viewModelScope.launch {
            _dao.insertContact(contact)
        }
    }
    // Challenge 1: Create a function to update a contact
    // Update an existing contact in the database
    // Same pattern as saveContact — background coroutine, non-blocking
    // The screen does not need to wait or refresh manually
    // The StateFlow automatically emits the updated list
    fun updateContact(contact: Contact){
        viewModelScope.launch {
            _dao.updateContact(contact)
        }
    }
    // Challenge 2: Create a function to delete a contact
    // Delete a contact from the database
    // Same pattern as save and update — background coroutine, non-blocking
    // After deletion the StateFlow emits a new list without that contact
    fun deleteContact(contact: Contact){
        viewModelScope.launch {
            _dao.deleteContact(contact)
        }
    }
}
