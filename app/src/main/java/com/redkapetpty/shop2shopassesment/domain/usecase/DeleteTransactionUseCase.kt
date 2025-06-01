package com.redkapetpty.shop2shopassesment.domain.usecase

import com.redkapetpty.shop2shopassesment.domain.model.Transaction
import com.redkapetpty.shop2shopassesment.domain.repository.TransactionRepository

class DeleteTransactionUseCase(
	private val repo: TransactionRepository
							  ) {
	suspend operator fun invoke(transaction: Transaction) {
		repo.delete(transaction)
	}
}