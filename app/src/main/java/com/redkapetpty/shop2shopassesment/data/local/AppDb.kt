package com.redkapetpty.shop2shopassesment.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [TransactionEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDb: RoomDatabase() {
	abstract fun txDao(): TransactionDao
}