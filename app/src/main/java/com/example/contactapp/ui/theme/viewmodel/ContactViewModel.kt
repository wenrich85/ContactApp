package com.example.contactapp.ui.theme.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactapp.data.Contact
import com.example.contactapp.data.ContactDatabase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.collections.emptyList


class ContactViewModel(application: Application) : AndroidViewModel(application) {
//    Geet the DAO from the database singleton
//    This is the single point of access to all the database operations
//    The DAO was created in the ContractDatabase class .getDatabase()
    private val _dao = ContactDatabase
        .getDatabase(context=application).contactDao()

    val contacts : StateFlow<List<Contact>> = _dao.getAllContacts().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

     fun save(contact: Contact){
         viewModelScope.launch {
             _dao.insertContact(contact)
         }
    }

    fun updateContact(contact: Contact) {
        viewModelScope.launch {
            _dao.updateContact(contact)
        }
    }

    fun deleteContact(contact: Contact){
        viewModelScope.launch {
            _dao.deleteContact(contact)
        }
    }



}