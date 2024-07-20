package com.example.contactapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.contactapp.ui.theme.ContactAppTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContactAppTheme {
                val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "Contact App") },
                            actions = {
                                val context = LocalContext.current
                                IconButton(onClick = { openDialer(context, "01098616820") }) {
                                    Icon(
                                        imageVector = Icons.Filled.Home,
                                        contentDescription = "01098616820"
                                    )
                                }
                            },
                            scrollBehavior = scrollBehavior,
                        )
                    }
                ) { innerPadding ->
                    ContactsList(
                        contacts = ContactList().contacts,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ContactsList(contacts: List<Contact>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier.padding(top = 10.dp)
    ) {
        items(contacts) { contact ->
            ContactsAppItem(contactItem = contact)
        }
    }
}

@Composable
fun ContactsAppItem(contactItem: Contact, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = contactItem.image),
            contentDescription = "contact photo",
            modifier = modifier
                .size(150.dp)
                .clickable {
                    val phoneNumber = contactItem.number
                    openDialer(context, phoneNumber)
                }
        )
        Text(
            text = contactItem.name,
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    val phoneNumber = contactItem.number
                    openDialer(context, phoneNumber)
                },
            textAlign = TextAlign.Center
        )
        SelectionContainer {
            Text(text = contactItem.number)
        }
    }
}

fun openDialer(context: Context, phoneNumber: String) {
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:$phoneNumber")
    }
    context.startActivity(intent)
}

@Preview(showBackground = true)
@Composable
fun ContactsAppPreview() {
    ContactAppTheme {
        ContactsList(contacts = ContactList().contacts)
    }
}
