package com.redkapetpty.shop2shopassesment

import com.redkapetpty.shop2shopassesment.data.local.TransactionEntity
import com.redkapetpty.shop2shopassesment.domain.model.AuditPolicy
import com.redkapetpty.shop2shopassesment.domain.model.Transaction
import com.redkapetpty.shop2shopassesment.domain.repository.AuditPolicyRepository
import com.redkapetpty.shop2shopassesment.domain.repository.TransactionRepository
import com.redkapetpty.shop2shopassesment.domain.usecase.AddTransactionUseCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.math.BigDecimal

class StubTxRepo : TransactionRepository {
	val list = mutableListOf<Transaction>()
	override fun observe() = flow { emit(list) }
	override suspend fun upsert(tx : Transaction): Result<Unit> {
		list += tx
		return Result.success(Unit)
	}
	override suspend fun delete(tx : Transaction) { list -= tx }
}

class StubPolicyRepo(private val p: AuditPolicy) : AuditPolicyRepository {
	override suspend fun get() = p
	override fun observe() = flowOf(p)
	override suspend fun set(policy: AuditPolicy) {	}
}

@OptIn(ExperimentalCoroutinesApi::class)
class AddTransactionUseCaseTest {

	@get:Rule val main = MainDispatcherRule(StandardTestDispatcher())

	@Test fun flags_large_amount() = runTest {
		val repo = StubTxRepo()
		val policy = AuditPolicy(BigDecimal("1000"))
		val prefs = StubPolicyRepo(policy)
		val useCase = AddTransactionUseCase(repo, prefs)

		useCase("Laptop", BigDecimal("1999"))
		val saved = repo.list.first()
		assertTrue(saved.flaggedForAudit)
	}
}