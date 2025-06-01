package com.redkapetpty.shop2shopassesment.data.repository

import com.redkapetpty.shop2shopassesment.data.local.TransactionDao
import com.redkapetpty.shop2shopassesment.data.local.TransactionEntity
import com.redkapetpty.shop2shopassesment.data.mapper.toDomain
import com.redkapetpty.shop2shopassesment.data.mapper.toDto
import com.redkapetpty.shop2shopassesment.data.mapper.toEntity
import com.redkapetpty.shop2shopassesment.data.remote.TxApi
import com.redkapetpty.shop2shopassesment.domain.model.Transaction
import com.redkapetpty.shop2shopassesment.domain.repository.TransactionRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class TransactionRepositoryImpl(private val dao: TransactionDao,
                                private val api: TxApi,
                                private val io: CoroutineDispatcher = Dispatchers.IO) : TransactionRepository {
	override fun observe(): Flow<List<Transaction>> =
		dao.observeAll().map { list -> list.map { it.toDomain() } }

	override suspend fun upsert(tx: Transaction) = withContext(io) {
		dao.upsert(tx.toEntity())
		runCatching { api.push(tx.toDto()) }
	}

	override suspend fun delete(tx: Transaction) = withContext(io) {
		dao.delete(tx.toEntity())
	}
}