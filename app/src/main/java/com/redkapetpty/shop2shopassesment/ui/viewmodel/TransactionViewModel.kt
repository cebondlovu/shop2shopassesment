package com.redkapetpty.shop2shopassesment.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redkapetpty.shop2shopassesment.core.functional.Resource
import com.redkapetpty.shop2shopassesment.core.functional.asResource
import com.redkapetpty.shop2shopassesment.domain.model.Transaction
import com.redkapetpty.shop2shopassesment.domain.usecase.AddTransactionUseCase
import com.redkapetpty.shop2shopassesment.domain.usecase.DeleteTransactionUseCase
import com.redkapetpty.shop2shopassesment.domain.usecase.ObserveTransactionUseCase
import com.redkapetpty.shop2shopassesment.domain.usecase.UpdateTransactionUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.math.BigDecimal

class TransactionViewModel(
	private val observeTx: ObserveTransactionUseCase,
	private val addTx: AddTransactionUseCase,
	private val delTx: DeleteTransactionUseCase,
	private val editTx  : UpdateTransactionUseCase ) : ViewModel() {

	val state = observeTx()
		.asResource()
		.stateIn(
			scope = viewModelScope,
			started = SharingStarted.WhileSubscribed(5_000),
			initialValue = Resource.Loading)


	fun add(title: String, amount: BigDecimal) = viewModelScope.launch {
		addTx(title, amount)
	}

	fun delete(tx: Transaction) = viewModelScope.launch { delTx(tx) }

	fun edit(original: Transaction, newTitle: String, newAmount: BigDecimal) =
		viewModelScope.launch {
			editTx(original.copy(title = newTitle, amount = newAmount))
		}
}