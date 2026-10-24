package com.example.contactappch11.ui.screens

import android.app.Application
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.contactappch11.data.Contact
import com.example.contactappch11.ui.components.ContactDialog
import com.example.contactappch11.ui.components.ContactItem
import com.example.contactappch11.viewModels.ContactViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListScreen(
    viewModel: ContactViewModel,
    onContactClick: (Contact) -> Unit,
) {
    val contacts by viewModel.contacts.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    if (showAddDialog) {
        ContactDialog(// Challenge the parameter to ContactDialog
            // contact = null means a new contact is being added
            contact = null,
            onConfirm = { newContact ->
                viewModel.saveContact(newContact)
                showAddDialog = false
            },
            onDismiss = { showAddDialog = false }
        )
    }// end of if (showAddDialog)
    //Scaffold provides the Material Design structure
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Contacts") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }, // end of topBar
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(
                    Icons.Filled.Add,

                    contentDescription = "Add contact",
                )
            }
        }// end of floatingActionButton
    ) { paddingValues ->
        // challenge if there are no contacts.
        if (contacts.isEmpty()) {
            // Challenge in a Box add a text that says "No contacts"
            // Show empty state message centered on screen
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No contacts yet.\nTap + to add one.",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } // end of if (contacts.isEmpty())
        else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(contacts, key = { it.id }) { contact ->
                    ContactItem(
                        contact = contact,
                        onClick = { onContactClick(contact) },
                        onDelete = { viewModel.deleteContact(contact) }
                    )
                }
            } // end of LazyColumn
        }
    }
}
