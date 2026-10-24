package com.example.contactappch11.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.overscroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.contactappch11.data.Contact

@Composable
fun ContactItem(
    contact: Contact,
    onClick: () -> Unit,
    onDelete: () -> Unit,
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    // Delete confirmation dialog
    if (showDeleteDialog) {
        // Challenge Create the delete confirmation dialog
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete contact") },
            text = { Text("Are you sure you want to delete ${contact.name}? This action cannot be undone.") },
            confirmButton = {
                Button(
                    onClick = {
                        onDelete()
                        showDeleteDialog = false
                    }
                ) { Text("Delete") }
            }, // end of confirmButton
            dismissButton = {
                Button(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            } // end of dismissButton
        ) // end of AlertDialog
    } // end of if (showDeleteDialog)
    // Cards wraps the contact in Material design card with elevation and styling
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clickable{onClick()},
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Challenge  add the Person avatar
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Person icon",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(  42.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            // Contact name and phone number
            Column(modifier = Modifier.weight(1f)) {
                //Challenge add the contact name
                Text(
                    text = contact.name,
                    style = MaterialTheme.typography.titleLarge
                )
                //Challenge add the contact phone number
                Text(
                    text = contact.phoneNumber,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }// end of Column for name and phone number
            // Challenge add the Delete button
            IconButton(onClick = { showDeleteDialog = true }) {
                Icon(
                     Icons.Default.Delete,
                    contentDescription = "Delete contact",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }

} // end of ContactItem function

@Preview (showBackground = true)
@Composable
fun ContactItemPreview(){
    ContactItem(
        contact = Contact(
            id = 1,
            name = "John doe",
            phoneNumber = "1234567890",),
        onClick = {},
        onDelete = {}
    )
}
