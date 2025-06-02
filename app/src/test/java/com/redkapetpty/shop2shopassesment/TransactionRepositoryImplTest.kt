package com.redkapetpty.shop2shopassesment

import com.redkapetpty.shop2shopassesment.data.local.TransactionDao
import com.redkapetpty.shop2shopassesment.data.local.TransactionEntity
import com.redkapetpty.shop2shopassesment.data.remote.TransactionDto
import com.redkapetpty.shop2shopassesment.data.remote.TxApi
import com.redkapetpty.shop2shopassesment.data.repository.TransactionRepositoryImpl
import com.redkapetpty.shop2shopassesment.domain.model.Transaction
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.math.BigDecimal

class FakeDao: TransactionDao {
	private val list = mutableListOf<TransactionEntity>()
	override fun observeAll() = flow { emit(list) }
	override suspend fun upsert(e: TransactionEntity) { list += e }
	override suspend fun delete(e: TransactionEntity) { list -= e }
}

class FakeApi : TxApi {
	val pushed = mutableListOf<TransactionDto>()
	override suspend fun push(tx: TransactionDto) { pushed += tx }
	override suspend fun pull() = emptyList<TransactionDto>()
}

@OptIn(ExperimentalCoroutinesApi::class)
class TransactionRepositoryImplTest {
	@get:Rule val main = MainDispatcherRule(StandardTestDispatcher())

	@Test fun upsert_persists_and_pushes() = runTest {
		val dao = FakeDao()
		val api = FakeApi()
		val repo = TransactionRepositoryImpl(dao, api)

		val tx = Transaction(title = "Test", amount = BigDecimal("1.0"))
		repo.upsert(tx)

		assertEquals(1, dao.observeAll().first().size)
		assertEquals(1, api.pushed.size)
		assertEquals("Test", api.pushed.first().title)
	}
}