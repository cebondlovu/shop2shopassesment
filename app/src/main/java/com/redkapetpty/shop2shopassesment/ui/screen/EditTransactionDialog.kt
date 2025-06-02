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
import com.redkapetpty.shop2shopassesment.domain.model.Transaction
import java.math.BigDecimal

@Composable
fun EditTransactionDialog(
	original : Transaction? = null,
	onConfirm: (String, BigDecimal) -> Unit,
	onDismiss: () -> Unit ) {
	var title  by remember(original) { mutableStateOf(original?.title ?: "") }
	var amount by remember(original) { mutableStateOf(original?.amount?.toPlainString() ?: "") }

	AlertDialog(
		onDismissRequest = onDismiss,
		title = { Text(if (original == null) "New Transaction" else "Edit Transaction") },
		text = {
			Column {
				OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
				Spacer(Modifier.height(8.dp))
				OutlinedTextField(
					value = amount,
					onValueChange = { amount = it },
					label = { Text("Amount") },
					keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
				                 )
			}
		},
		confirmButton = {
			TextButton(onClick = {
				onConfirm(title.trim(), amount.toBigDecimalOrNull() ?: BigDecimal.ZERO)
			}) { Text(if (original == null) "Save" else "Update") }
		},
		dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
	           )
}