package com.redkapetpty.shop2shopassesment.data.mapper

import com.redkapetpty.shop2shopassesment.data.local.TransactionEntity
import com.redkapetpty.shop2shopassesment.data.remote.TransactionDto
import com.redkapetpty.shop2shopassesment.domain.model.Transaction
import java.math.BigDecimal

fun TransactionEntity.toDomain() = Transaction(
	id = id,
	title = title,
	amount = amount,
	timestamp = timestamp,
	flaggedForAudit = flaggedForAudit)

fun Transaction.toEntity() = TransactionEntity(
	id = id,
	title = title,
	amount = amount,
	timestamp = timestamp,
	flaggedForAudit = flaggedForAudit)

fun Transaction.toDto() = TransactionDto(
	id = id,
	title = title,
	amount = amount,
	timestamp = timestamp,
	flaggedForAudit = flaggedForAudit)