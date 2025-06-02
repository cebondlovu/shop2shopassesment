package com.redkapetpty.shop2shopassesment.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redkapetpty.shop2shopassesment.core.functional.Resource
import com.redkapetpty.shop2shopassesment.core.functional.asResource
import com.redkapetpty.shop2shopassesment.domain.model.Transaction
import com.redkapetpty.shop2shopassesment.domain.usecase.AddTransactionUseCase
import com.redkapetpty.shop2shopassesment.domain.usecase.DeleteTransactionUseCase
import com.redkapetpty.shop2shopassesment.domain.usecase.ObserveTransactionUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.math.BigDecimal

class TransactionViewModel(
	private val observeTx: ObserveTransactionUseCase,
	private val addTx: AddTransactionUseCase,
	private val delTx: DeleteTransactionUseCase) : ViewModel() {

		val state = observeTx()
			.asResource()
			.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Resource.Loading)

	fun add(title:String, amount: BigDecimal) = viewModelScope.launch {  addTx(title, amount) }

	fun delete(tx: Transaction) = viewModelScope.launch { delTx(tx) }
}