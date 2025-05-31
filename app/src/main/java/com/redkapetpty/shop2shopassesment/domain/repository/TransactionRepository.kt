package com.redkapetpty.shop2shopassesment.domain.repository

import com.redkapetpty.shop2shopassesment.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

/** Add proper comments once done */
interface TransactionRepository {
	fun observe(): Flow<List<Transaction>>
	suspend fun upsert(tx: Transaction)
	suspend fun delete(tx: Transaction)
}
