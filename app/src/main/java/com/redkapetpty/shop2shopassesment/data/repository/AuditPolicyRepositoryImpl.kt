package com.redkapetpty.shop2shopassesment.data.repository

import androidx.datastore.core.DataStore
import com.redkapetpty.shop2shopassesment.data.datastore.AuditPolicyProto
import com.redkapetpty.shop2shopassesment.data.mapper.auditPolicyToDomain
import com.redkapetpty.shop2shopassesment.data.mapper.toProto
import com.redkapetpty.shop2shopassesment.domain.model.AuditPolicy
import com.redkapetpty.shop2shopassesment.domain.repository.AuditPolicyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class AuditPolicyRepositoryImpl(
	private val store: DataStore<AuditPolicyProto>
							   ): AuditPolicyRepository {
	override suspend fun get(): AuditPolicy =
		store.data.first().auditPolicyToDomain()

	override fun observe(): Flow<AuditPolicy> =
		store.data.map { it.auditPolicyToDomain() }

	override suspend fun set (policy :AuditPolicy) {
		store.updateData { policy.toProto() }
	}
}