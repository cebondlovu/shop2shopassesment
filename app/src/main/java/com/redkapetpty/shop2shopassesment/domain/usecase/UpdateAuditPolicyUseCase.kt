package com.redkapetpty.shop2shopassesment.domain.usecase

import com.redkapetpty.shop2shopassesment.domain.model.AuditPolicy
import com.redkapetpty.shop2shopassesment.domain.repository.AuditPolicyRepository

class UpdateAuditPolicyUseCase(
	private val prefs: AuditPolicyRepository
							  ) {
	suspend operator fun invoke(newPolicy: AuditPolicy) {
		prefs.set(newPolicy)
	}
}