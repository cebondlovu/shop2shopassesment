package com.redkapetpty.shop2shopassesment.data.repository

import com.redkapetpty.shop2shopassesment.data.local.TransactionDao
import com.redkapetpty.shop2shopassesment.data.remote.TxApi
import com.redkapetpty.shop2shopassesment.domain.model.Transaction
import com.redkapetpty.shop2shopassesment.domain.repository.TransactionRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class TransactionRepositoryImpl(private val dao: TransactionDao,
                                private val api: TxApi,
                                private val io: CoroutineDispatcher = Dispatchers.IO) : TransactionRepository {
	override fun observe(): Flow<List<Transaction>> {
		TODO("Not yet implemented")
	}

	override suspend fun upsert(tx: Transaction) {
		TODO("Not yet implemented")
	}

	override suspend fun delete(tx: Transaction) {
		TODO("Not yet implemented")
	}
}