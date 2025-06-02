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
fun TransactionListScreen(
	nav: NavController,
	vm : TransactionViewModel = koinViewModel()
                         ) {
	val state by vm.state.collectAsState()
	var dialogMode by remember { mutableStateOf<DialogMode?>(null) }

	Scaffold(
		topBar = {
			TopAppBar(
				title = { Text("Transactions") },
				actions = {
					IconButton(onClick = { nav.navigate("settings") }) {
						Icon(Icons.Default.Settings, null)
					}
				}
			         )
		},
		floatingActionButton = {
			FloatingActionButton(onClick = { dialogMode = DialogMode.Add }) {
				Icon(Icons.Default.Add, null)
			}
		}
	        ) { pad ->
		Box(Modifier.padding(pad)) {
			when (state) {
				is Resource.Loading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
				is Resource.Error   -> Text("Error", Modifier.align(Alignment.Center))
				is Resource.Success -> {
					val list = (state as Resource.Success<List<Transaction>>).data
					LazyColumn {
						items(list, key = { it.id }) { tx ->
							ListItem(
								headlineContent = { Text(tx.title) },
								supportingContent = {
									Text("R ${tx.amount}")
									if (tx.flaggedForAudit) Text("⚠️ Audit")
								},
								leadingContent = {
									Icon(
										imageVector = Icons.Default.Delete,
										contentDescription = null,
										modifier = Modifier.clickable { vm.delete(tx) }
									    )
								},
								modifier = Modifier.clickable { dialogMode = DialogMode.Edit(tx) }
							        )
							Divider()
						}
					}
				}
			}
		}
	}

	when (val m = dialogMode) {
		DialogMode.Add -> EditTransactionDialog(
			onConfirm = { t, a ->
				vm.add(t, a)
				dialogMode = null
			},
			onDismiss = { dialogMode = null }
		                                       )
		is DialogMode.Edit -> EditTransactionDialog(
			original = m.tx,
			onConfirm = { t, a ->
				vm.edit(m.tx, t, a)
				dialogMode = null
			},
			onDismiss = { dialogMode = null }
		                                           )
		null -> {}
	}
}

private sealed interface DialogMode {
	object Add : DialogMode
	data class Edit(val tx: Transaction) : DialogMode
}
