package com.redkapetpty.shop2shopassesment.ui.preview

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.redkapetpty.shop2shopassesment.core.functional.Resource
import com.redkapetpty.shop2shopassesment.domain.model.AuditPolicy
import com.redkapetpty.shop2shopassesment.domain.model.Transaction
import com.redkapetpty.shop2shopassesment.domain.repository.AuditPolicyRepository
import com.redkapetpty.shop2shopassesment.domain.repository.TransactionRepository
import com.redkapetpty.shop2shopassesment.domain.usecase.AddTransactionUseCase
import com.redkapetpty.shop2shopassesment.domain.usecase.DeleteTransactionUseCase
import com.redkapetpty.shop2shopassesment.domain.usecase.ObserveTransactionUseCase
import com.redkapetpty.shop2shopassesment.domain.usecase.UpdateAuditPolicyUseCase
import com.redkapetpty.shop2shopassesment.domain.usecase.UpdateTransactionUseCase
import com.redkapetpty.shop2shopassesment.ui.screen.AddTransactionDialog
import com.redkapetpty.shop2shopassesment.ui.screen.AuditPolicyScreen
import com.redkapetpty.shop2shopassesment.ui.screen.EditTransactionDialog
import com.redkapetpty.shop2shopassesment.ui.screen.TransactionListScreen
import com.redkapetpty.shop2shopassesment.ui.viewmodel.SettingsViewModel
import com.redkapetpty.shop2shopassesment.ui.viewmodel.TransactionViewModel
import kotlinx.coroutines.ExperimentalForInheritanceCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.math.BigDecimal

//this requires as much as our unit tests with fake implementations, I think we will need a preview di module for koin etc


// lets add our fake implementations
private val sampleTx = listOf(
	Transaction(title = "Coffee", amount = BigDecimal("45.00")),
	Transaction(title = "Laptop", amount = BigDecimal("12999.99"), flaggedForAudit = true))

private class PreviewTxRepo : TransactionRepository {
	private val flow = MutableStateFlow(sampleTx)
	override fun observe() = flow
	override suspend fun upsert(tx : Transaction): Result<Unit> { return Result.success(Unit) }
	override suspend fun delete(tx : Transaction) {  }
}

private class PreviewPolicyRepo: AuditPolicyRepository {
	private val policy = MutableStateFlow(
		AuditPolicy(BigDecimal("1000"), setOf("cash", "gift")))

	override suspend fun get() = policy.value
	override fun observe() = policy
	override suspend fun set(policy: AuditPolicy) { this.policy.value = policy }
}

private val observeUC = ObserveTransactionUseCase(PreviewTxRepo())
private val addUC = AddTransactionUseCase(PreviewTxRepo(), PreviewPolicyRepo())
private val deleteUC = DeleteTransactionUseCase(PreviewTxRepo())
private val updateUC = UpdateAuditPolicyUseCase(PreviewPolicyRepo())
private val editUC = UpdateTransactionUseCase(PreviewTxRepo())

@OptIn(ExperimentalForInheritanceCoroutinesApi::class)
private val txVM = TransactionViewModel(observeUC, addUC, deleteUC, editUC).apply {
	@Suppress("UNCHECKED_CAST")
	(this as Object).javaClass
		.getDeclaredField("state")
		.apply { isAccessible = true }
		.set(this, object : StateFlow<Resource<List<Transaction>>> {
			private val backing = MutableStateFlow(Resource.Success(sampleTx))
			override val value get() = backing.value
			override val replayCache get() = backing.replayCache

			override suspend fun collect(collector: FlowCollector<Resource<List<Transaction>>>) =
				backing.collect(collector)

		})
}

private val settingsVM = SettingsViewModel(PreviewPolicyRepo(), updateUC)

@Composable private fun TransactionListScreenPreviewContent() {
	MaterialTheme {
		TransactionListScreen(nav = rememberNavController(), vm = txVM)
	}
}

@Composable private fun AddTransactionDialogPreviewContent() {
	MaterialTheme {
		AddTransactionDialog(onConfirm = {_, _-> }, onDismiss = {})
	}
}

@Composable private fun EditTransactionDialogPreviewContent() {
	MaterialTheme {
		EditTransactionDialog(onConfirm = {_, _-> }, onDismiss = {})
	}
}

@Composable private fun AuditPolicyPreviewContent() {
	MaterialTheme {
		AuditPolicyScreen(nav = rememberNavController(), vm = settingsVM)
	}
}

@Preview(showBackground = true, showSystemUi = true, name = "TransactionListScreen")
@Composable
fun TransactionListScreenPreview() = TransactionListScreenPreviewContent()

@Preview(showBackground = true, name = "AddTransactionDialog")
@Composable
fun AddTransactionDialogPreview() = AddTransactionDialogPreviewContent()

@Preview(showBackground = true, name = "AuditPolicyScreen")
@Composable
fun AuditPolicyPreview() = AuditPolicyPreviewContent()

@Preview(showBackground = true, name = "EditTransactionDialog")
@Composable
fun EditTransactionDialogPreview() = EditTransactionDialogPreviewContent()
