package com.redkapetpty.shop2shopassesment.data.remote

import kotlinx.coroutines.delay
import okhttp3.Interceptor
import okhttp3.Response

class FakeInterceptor: Interceptor {
	override fun intercept(chain: Interceptor.Chain): Response {
		val response = chain.proceed(chain.request())
		Thread.sleep(500)
		return response
	}

}