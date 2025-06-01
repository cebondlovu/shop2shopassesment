package com.redkapetpty.shop2shopassesment.domain.usecase

import com.redkapetpty.shop2shopassesment.domain.model.Transaction
import com.redkapetpty.shop2shopassesment.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow

class ObserveTransactionUseCase(
	private val repo: TransactionRepository
							   ) {
	operator fun invoke(): Flow<List<Transaction>> = repo.observe()
}