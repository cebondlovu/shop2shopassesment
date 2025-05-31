package com.redkapetpty.shop2shopassesment.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "transactions")
data class TransactionEntity(
	@PrimaryKey(autoGenerate = true) val id: Long = 0,
	val title: String,
	val amount: BigDecimal,
	val timestamp: Long,
	val flaggedForAudit: Boolean )
