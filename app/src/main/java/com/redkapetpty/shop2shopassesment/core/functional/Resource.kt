package com.redkapetpty.shop2shopassesment.core.functional

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed interface Resource<out T> {
	data class Success<T>(val data: T) : Resource<T>
	data class Error(val throwable: Throwable) : Resource<Nothing>
	data object  Loading:  Resource<Nothing>
}

inline fun <T> Flow<T>.asResource(): Flow<Resource<T>> = map<T, Resource<T>> {Resource.Success(it)}
	.onStart { emit(Resource.Loading) }
	.catch { emit(Resource.Error(it)) }