package com.example.contactapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.contactapp.data.Contact
import com.example.contactapp.ui.viewmodel.ContactViewModel
import androidx.compose.material3.Icon
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.contactapp.ui.components.ContactDialog
import com.example.contactapp.ui.components.ContactItem


// Main screen for the contact list-- displays all contacts from the viewModel in a scrollable list
// /viewModel: provide the contacts StateFlow to the ContactListScreen
// onContactClick: callback when a contact is clicked to the contact details screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListScreen(
    viewModel: ContactViewModel,
    onContactClick: (Contact) -> Unit,
){
    //Collect the Contacts StateFlow as a Compose State
    //Every time the viewModel emits a new list, this recomposes the screen and updates the list
    val contacts by viewModel.contacts.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }

    if (showAddDialog){
        ContactDialog(
            contact = null,
            onConfirm = {
                newContact ->
                viewModel.save(newContact)
                showAddDialog = false
            },
            onDismiss = { showAddDialog = false}
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text("Contacts")},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },// end TopBar
        floatingActionButton = {
            FloatingActionButton(onClick = {showAddDialog = true}) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Contact")
            }
        }

    )//Ende Scaffold Parameters
    {
        padding ->
        //check if there are any contacts
        if(contacts.isEmpty()){
            //Show in a box a state message centered on screen
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center

        ){
            Text(
                text = "No contact yet. \n Tap the + button to add a contact.",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        } else {
            //show list of contacs
            //lazy column only renders visible items on the screen
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(contacts, key = {it.id})  {contact ->
                    ContactItem(
                        contact = contact,
                        //When the user taps the card, navigate to the contact details screen
                        onClick={onContactClick(contact)},
                        //when the user taps the delete icon, delete from the database
                        //viewmodel launches the coroutine in the background
                        onDelete = {viewModel.deleteContact(contact)}
                    )

                }
            }
        }
    }//end Scaffold
}