package com.redkapetpty.shop2shopassesment.domain.repository

import com.redkapetpty.shop2shopassesment.domain.model.AuditPolicy
import kotlinx.coroutines.flow.Flow

/** Add proper comments once done */
interface AuditPolicyRepository {
	suspend fun  get(): AuditPolicy
	fun observe(): Flow<AuditPolicy>
	// this will automatically transform the stored policy
	suspend fun  update(transform: (AuditPolicy) -> AuditPolicy)
}
