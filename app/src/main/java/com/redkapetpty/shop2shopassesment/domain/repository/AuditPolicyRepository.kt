package com.redkapetpty.shop2shopassesment.domain.repository

import com.redkapetpty.shop2shopassesment.domain.model.AuditPolicy
import kotlinx.coroutines.flow.Flow

/** Add proper comments once done */
interface AuditPolicyRepository {
	suspend fun  get(): AuditPolicy
	fun observe(): Flow<AuditPolicy>
	suspend fun set(policy: AuditPolicy)
}
