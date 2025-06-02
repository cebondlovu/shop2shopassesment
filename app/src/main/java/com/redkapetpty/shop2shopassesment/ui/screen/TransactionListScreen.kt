package com.redkapetpty.shop2shopassesment.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.redkapetpty.shop2shopassesment.core.functional.Resource
import com.redkapetpty.shop2shopassesment.domain.model.Transaction
import com.redkapetpty.shop2shopassesment.ui.viewmodel.TransactionViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionListScreen(nav: NavController, vm: TransactionViewModel = koinViewModel()) {
	// will change the by to a = if I run into issue while running
	val state by vm.state.collectAsState()
	var showDialog by remember { mutableStateOf(false) }

	Scaffold (
		topBar = {
			TopAppBar(
				title = { Text("Transactions") },
				actions = {
				IconButton(onClick = { nav.navigate("settings") }) {
					Icon(Icons.Default.Settings, null)
				}
			})
		},
		floatingActionButton = {
			FloatingActionButton(onClick = { showDialog = true }) {
				Icon(Icons.Default.Add, null)
			}
		}) { pad ->
		Box(Modifier.padding(pad)) {
			when(state) {
				is Resource.Loading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
				is Resource.Error -> Text("Error", Modifier.align(Alignment.Center))
				is Resource.Success -> {
					val list = (state as Resource.Success<List<Transaction>>).data
					LazyColumn {
						items(list) { tx ->
							ListItem(
								headlineContent = { Text(tx.title)},
								supportingContent = {
									Text("R ${tx.amount}")
									if (tx.flaggedForAudit) Text("Audit")
								},
								leadingContent = {
									Icon(Icons.Default.Delete, null,
									     Modifier.clickable{ vm.delete(tx)})}
									)
							Divider()
						}
					}
				}
			}
		}
	}

	if(showDialog) AddTransactionDialog (
		onConfirm = { title, amount ->
			vm.add(title, amount)
			showDialog = false
		},
		onDismiss = { showDialog = false})
}

