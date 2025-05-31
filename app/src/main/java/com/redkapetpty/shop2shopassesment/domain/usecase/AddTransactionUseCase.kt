package com.redkapetpty.shop2shopassesment.domain.usecase

import com.redkapetpty.shop2shopassesment.domain.model.AuditPolicy
import com.redkapetpty.shop2shopassesment.domain.model.Transaction
import com.redkapetpty.shop2shopassesment.domain.repository.AuditPolicyRepository
import com.redkapetpty.shop2shopassesment.domain.repository.TransactionRepository
import java.math.BigDecimal

/** Add proper comments once done */
class AddTransactionUseCase(
	private val repo: TransactionRepository,
	private val prefs: AuditPolicyRepository ) {
	suspend operator fun invoke(title: String, amount: BigDecimal) {
		val policy = prefs.get()
		val tx = Transaction(
			title = title,
			amount = amount).let { it.copy(flaggedForAudit = policy.shouldFlag(it))}
		repo.upsert(tx)
	}
}