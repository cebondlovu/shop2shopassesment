package com.redkapetpty.shop2shopassesment.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redkapetpty.shop2shopassesment.domain.model.AuditPolicy
import com.redkapetpty.shop2shopassesment.domain.repository.AuditPolicyRepository
import com.redkapetpty.shop2shopassesment.domain.usecase.UpdateAuditPolicyUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.math.BigDecimal

class SettingsViewModel(
	prefs: AuditPolicyRepository,
	private val update: UpdateAuditPolicyUseCase): ViewModel() {
	val policy = prefs.observe()
		.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AuditPolicy())

	fun update(threshold: BigDecimal, keywords: Set<String>) = viewModelScope.launch {
		update(AuditPolicy(threshold, keywords))
	}
}