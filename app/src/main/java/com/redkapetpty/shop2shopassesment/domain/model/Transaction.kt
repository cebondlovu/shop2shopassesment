package com.redkapetpty.shop2shopassesment.domain.model

import java.math.BigDecimal

data class Transaction (
	val id: Long = 0,
	val title: String,
	val amount: BigDecimal,
	val timestamp: Long = System.currentTimeMillis(),
	val flaggedForAudit: Boolean = false)
