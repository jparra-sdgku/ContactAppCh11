package com.example.contactappch11

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.contactappch11.data.Contact
import com.example.contactappch11.ui.screens.ContactDetailScreen
import com.example.contactappch11.ui.screens.ContactListScreen
import com.example.contactappch11.ui.theme.ContactAppCh11Theme
import com.example.contactappch11.viewModels.ContactViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: ContactViewModel = viewModel()
            val selectedContact = remember { mutableStateOf<Contact?>(null) }
            ContactAppCh11Theme {
                MaterialTheme {
                    if (selectedContact.value == null) {
                        ContactListScreen(
                            viewModel = viewModel,
                            onContactClick = {
                                contact -> selectedContact.value = contact
                            }
                        )
                    } // end of if (selectedContact.value == null)
                    else {
                        ContactDetailScreen(
                            contactId = selectedContact.value!!.id,
                            viewModel = viewModel,
                            onBackClick = {
                                selectedContact.value = null
                            }
                        )
                    }
                }
            }
        }
    }
}

