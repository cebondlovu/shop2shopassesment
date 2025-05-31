package com.redkapetpty.shop2shopassesment.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface TxApi {
	@POST("transactions") suspend fun push(@Body tx: TransactionDto)
	@GET("transactions") suspend fun pull(): List<TransactionDto>
}
