package com.example.contactappch11.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.contactappch11.data.Contact


@Composable
fun ContactDialog(
    contact: Contact? = null,
    onConfirm: (Contact) -> Unit,
    onDismiss: () -> Unit
) {
    // Local variables for three input fields
    var name by remember { mutableStateOf(contact?.name ?: "") }
    // Challenge create local's for phone number and email
    var phoneNumber by remember { mutableStateOf(contact?.phoneNumber ?: "") }
    var email by remember { mutableStateOf(contact?.email ?: "") }

    // Determine the title based on whether we are editing an existing contact or adding a new one
    // contact == null means we are adding a new contact, otherwise we are editing an existing one
    val title = if (contact == null) "Add contact" else "Edit contact"

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title) },
        text = {
            Column(modifier = Modifier.padding(8.dp)) {
                // Input field for name
                //Challenge: add input fields for phone number and email
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true,
                    shape = MaterialTheme.shapes.small,
                    supportingText = {
                        Text(text = "Required")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                // Input field for phone number
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = { Text("Phone number") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true,
                    shape = MaterialTheme.shapes.small,
                    supportingText = {
                        Text(text = "Required")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                // Input field for email
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email optional") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )// enf of OutlinedTextField for email field
            } // end of Column for text fields
        }, // end of text parameter for AlertDialog
        confirmButton = {
            Button(
                onClick = {
                    val newContact = Contact(
                        id = contact?.id ?: 0,
                        name = name,
                        phoneNumber = phoneNumber,
                        email = email
                    ) // end of newContact variable
                    onConfirm(newContact)
                }
            ) { Text("Save") }
        },
        //challenge add dismiss button to AlertDialog

        dismissButton = {
            Button(onClick = onDismiss) { Text("Cancel")
            }
        }
    ) // end of AlertDialog parameter
} // end of ContactDialog function

