package com.redkapetpty.shop2shopassesment.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.math.BigDecimal

@Composable
fun AddTransactionDialog(
	onConfirm: (String, BigDecimal ) -> Unit,
	onDismiss: () -> Unit) {

	var title by remember { mutableStateOf("") }
	var amount by remember { mutableStateOf("") }

	AlertDialog(
		onDismissRequest = onDismiss,
		title = { Text("New Transaction") },
		text = {
			Column {
				OutlinedTextField(value = title, onValueChange = { title = it}, label = {Text("Title")})
				Spacer(Modifier.height(8.dp))
				OutlinedTextField(
					value = amount,
					onValueChange = { amount = it},
					label = { Text("Amount")},
					keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
			}
		},
		confirmButton = {
			TextButton(onClick = {
				onConfirm(title, amount.toBigDecimalOrNull() ?: BigDecimal.ZERO)
			}) {Text("Save") }
		},
		dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
			   )

}