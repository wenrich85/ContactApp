package com.example.contactapp.ui.screens

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.contactapp.ui.components.ContactDialog
import com.example.contactapp.ui.viewmodel.ContactViewModel

@Composable
fun ContactDetailScreen(
    contactId: Int,
    viewModel: ContactViewModel,
    onBackClick: () -> Unit
){
    val contacts by viewModel.contacts.collectAsState()
    val contact = contacts.firstOrNull { it.id == contactId}

    if(contact == null){
        onBackClick()
        return
    }
    var showEditDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    if(showDeleteDialog){
        ContactDialog(
            contact = contact,
            onConfirm = {viewModel.updateContact(contact)},
            onDismiss = {showEditDialog = false},


        )
    }

    AlertDialog(
        onDismissRequest = { showDeleteDialog = false},
        title = { Text("Delete Contact") },
        text = {Text("Delete ${contact.name}? This cannot be undone.")},
        confirmButton = {
            TextButton(
                onClick = {
                    viewModel.deleteContact(contact)
                }
            ) {
                Text("Delete", color = MaterialTheme.colorScheme.error)
            }
        } ,
        dismissButton = {
            TextButton(
                onClick = {
                    showDeleteDialog = false
                }
            ) {

                Text("Cancel")
            }
        }
    )
}


