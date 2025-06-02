package com.redkapetpty.shop2shopassesment

import android.content.Context
import androidx.datastore.core.DataStoreFactory
import androidx.test.core.app.ApplicationProvider
import com.redkapetpty.shop2shopassesment.data.datastore.AuditPolicySerializer
import com.redkapetpty.shop2shopassesment.data.repository.AuditPolicyRepositoryImpl
import com.redkapetpty.shop2shopassesment.domain.model.AuditPolicy
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.File
import java.math.BigDecimal

@RunWith(RobolectricTestRunner::class)
class AuditPolicyRepositoryTest {

	@get:Rule val main = MainDispatcherRule()

	private val context = ApplicationProvider.getApplicationContext<Context>()
	private val store = DataStoreFactory.create(
		serializer = AuditPolicySerializer,
		produceFile = {File(context.cacheDir, "audit_policy_test.pb")} )
	private val repo = AuditPolicyRepositoryImpl(store)

	@Test fun round_trip() = runTest {
		val newPolicy = AuditPolicy(BigDecimal("500"), setOf("cash"))
		repo.set(newPolicy)
		val readBack = repo.get()
		assertEquals(newPolicy, readBack)
	}

	@Test fun observe_emits_changes() = runTest {
		val initial = repo.observe().first()
		val updated = initial.copy(threshold = BigDecimal("2000"))
		repo.set(updated)
		val second = repo.observe().first()
		assertEquals(updated, second)
	}
}