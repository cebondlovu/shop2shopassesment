package com.redkapetpty.shop2shopassesment.data.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object AuditPolicySerializer : Serializer<AuditPolicyProto> {
	override val defaultValue: AuditPolicyProto = AuditPolicyProto.getDefaultInstance()

	override suspend fun readFrom(input: InputStream): AuditPolicyProto =
		try {
			AuditPolicyProto.parseFrom(input)
		}catch (e: InvalidProtocolBufferException) {
			throw CorruptionException("cannot read proto", e)
		}

	override suspend fun writeTo(
		t: AuditPolicyProto, output: OutputStream
	                            ) = t.writeTo(output)
}