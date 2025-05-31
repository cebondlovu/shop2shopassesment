package com.redkapetpty.shop2shopassesment.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
	@Query("SELECT * FROM transactions ORDER BY timestamp DESC")
	fun observeAll(): Flow<List<TransactionEntity>>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun upsert(e: TransactionEntity)

	@Delete suspend fun delete(e: TransactionEntity)
}