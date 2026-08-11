package com.example.contactapp.ui.theme.components

import android.view.Display
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.room.util.TableInfo
import com.example.contactapp.data.Contact

@Composable
fun ContactDialog(
    contact: Contact? = null,
    onConfirm: (Contact) -> Unit,
    onDismiss: () -> Unit
){
    var name by remember { mutableStateOf(contact?.name ?: "")}
    var phoneNumber by remember { mutableStateOf(value = contact?.phoneNumber ?: "") }
    var email by remember {mutableStateOf(value = contact?.email)}

    val title = if(contact != null){
        "Edit Contact"
    }else {
        //create new contact
        "Add Contact"
    }


    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title)},
        text = {
            Column(modifier = Modifier.padding(all = 8.dp)){
                OutlinedTextField(
                    value = name,
                    onValueChange = {name = it },
                    label = "Enter a Name",
                    require = true,
                    singleLine = true
                )
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = "Enter a Name",
                    require = true,
                    singleLine = true
                )
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = {phoneNumber = it },
                    label = "(123)456-7890",
                    singleLine = true
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = {email = it},
                    label = "Add email",
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val newContact = Contact(
                        id = contact?.id ?: 0,
                        name = name,
                        phoneNumber = phoneNumber,
                        email = email
                    )
                    onConfirm(newContact)
                }
            )// End Button parameters
            {
                Text("Save")
            }
        },

        dismissButton = {
            Button(onClick = onDismiss){
                Text("Cancel")
            }
        }
    ) //End alert dialog

}