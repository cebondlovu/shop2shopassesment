package com.redkapetpty.shop2shopassesment.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.redkapetpty.shop2shopassesment.ui.viewmodel.SettingsViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuditPolicyScreen(nav: NavController, vm: SettingsViewModel = koinViewModel()) {
	val policy by vm.policy.collectAsState()

	var threshold by remember(policy) { mutableStateOf(policy.threshold.toPlainString())}
	var keyword by remember { mutableStateOf("") }
	var keywords by remember(policy) { mutableStateOf(policy.keywords.toMutableSet())  }

	Scaffold (
		topBar = { TopAppBar(title = {"Audit Policy"}) },
		floatingActionButton = {
			FloatingActionButton(onClick = {
				vm.update(threshold.toBigDecimalOrNull() ?: policy.threshold, keywords)
				nav.popBackStack()
			}) { Icon(Icons.Default.Settings, null) }
		}) { pad ->
		Column(Modifier.padding(pad).padding(16.dp)) {
			OutlinedTextField(
				value = threshold,
				onValueChange = { threshold = it},
				label = { Text("Amount Threshold (R)")})
			Spacer(Modifier.height(8.dp))
			keywords.forEach { k ->
				Row(verticalAlignment = Alignment.CenterVertically) {
					Text(k, Modifier.weight(1f))
					IconButton(onClick = { keywords.remove(k)}) {
						Icon(Icons.Default.Delete, null)
					}
				}
			}
			OutlinedTextField(
				value = keyword,
				onValueChange = { keyword = it },
				label = { Text("Add Keyword")},
				modifier = Modifier.fillMaxWidth())
			Button(
				onClick = {
					if(keyword.isNotBlank()) {
						keywords += keyword.trim()
						keyword = ""
					}
				},
				modifier = Modifier.align(Alignment.End).padding(top = 4.dp)) { Text("Add") }
		}
	}
}