package com.example.contactappch11.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.contactappch11.ui.components.ContactDialog
import com.example.contactappch11.viewModels.ContactViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDetailScreen(
    contactId: Int,
    viewModel: ContactViewModel,
    onBackClick: () -> Unit
) {
    val contacts by viewModel.contacts.collectAsState()
    val contact = contacts.find { it.id == contactId }
    // Challenge if contact was delete, go back to list
    if (contact == null) {
        onBackClick()
        return // end the function if contact is null
    }

    var showEditDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    // Challenge call the ContactDialog function to edit the contact
    if (showEditDialog) {
        ContactDialog(
            contact = contact,
            onConfirm = { updatedContact ->
                viewModel.updateContact(updatedContact)
                showEditDialog = false
            },
            onDismiss = { showEditDialog = false }
        )
    }
    // Challenge call the Delete confirmation dialog
    if (showDeleteDialog) {
        // Challenge Create the delete confirmation dialog
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete contact") },
            text = { Text("Delete ${contact.name}? This action cannot be undone.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteContact(contact)
                        showDeleteDialog = false
                        onBackClick()
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
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showEditDialog = true }) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Edit contact"
                        )
                    }
                    // Challenge add the Delete button
                    IconButton(onClick = { showDeleteDialog = true })
                    {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete contact"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Challenge add the contact name
            Text(
                text = contact.name,
                style = MaterialTheme.typography.headlineLarge
            )
            HorizontalDivider()
            // Challenge add the contact phone number
            Text(
                text = contact.phoneNumber,
                style = MaterialTheme.typography.headlineSmall
            )
            // Challenge add the contact email
            if (contact.email.isNotEmpty()) {
                Text(
                    text = contact.email,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }

    }

}
