package com.redkapetpty.shop2shopassesment.domain.model

import java.math.BigDecimal

data class AuditPolicy(
	val threshold: BigDecimal = BigDecimal("1000"),
	val keywords: Set<String> = emptySet() ) {
	fun shouldFlag(tx: Transaction) =
		tx.amount >= threshold || keywords.any { it in tx.title.lowercase() }
}
