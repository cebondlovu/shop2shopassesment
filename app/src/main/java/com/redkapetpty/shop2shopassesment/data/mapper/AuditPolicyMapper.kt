package com.redkapetpty.shop2shopassesment.data.mapper

import com.redkapetpty.shop2shopassesment.data.datastore.AuditPolicyProto
import com.redkapetpty.shop2shopassesment.domain.model.AuditPolicy
import java.math.BigDecimal

fun AuditPolicyProto.auditPolicyToDomain() = AuditPolicy(
	threshold = BigDecimal(threshold),
	keywords = keywordsList.toSet())

fun AuditPolicy.toProto() = AuditPolicyProto.newBuilder()
	.setThreshold(threshold.toPlainString())
	.addAllKeywords(keywords)
	.build()