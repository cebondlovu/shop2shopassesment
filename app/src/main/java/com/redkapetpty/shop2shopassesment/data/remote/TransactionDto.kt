package com.redkapetpty.shop2shopassesment.data.remote

import com.squareup.moshi.JsonClass
import java.math.BigDecimal

@JsonClass(generateAdapter = true)
data class TransactionDto(
	val id: Long,
	val title: String,
	val amount: BigDecimal,
	val timestamp: Long,
	val flaggedForAudit: Boolean)
