package com.redkapetpty.shop2shopassesment

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.redkapetpty.shop2shopassesment.data.local.AppDb
import com.redkapetpty.shop2shopassesment.data.local.Converters
import com.redkapetpty.shop2shopassesment.data.local.TransactionEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.math.BigDecimal

@RunWith(RobolectricTestRunner::class)
class TransactionDaoTest {
	@get:Rule val instant = InstantTaskExecutorRule()
	@get:Rule val main = MainDispatcherRule()

	private val db = Room.inMemoryDatabaseBuilder(
		ApplicationProvider.getApplicationContext(), AppDb::class.java)
		.allowMainThreadQueries().addTypeConverter(Converters())
		.build()

	private val dao = db.txDao()

	@After fun tearDown() = db.close()

	@Test fun upsert_and_observe() = runTest {
		val entity = TransactionEntity(
			title = "coffee",
			amount = BigDecimal("45.00"),
			timestamp = 0L,
			flaggedForAudit = false)
		dao.upsert(entity)

		val list = dao.observeAll().first()
		assertEquals(1, list.size)
		assertEquals("coffee", list.first().title)
	}
}