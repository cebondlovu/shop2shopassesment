package com.redkapetpty.shop2shopassesment.core.di

import android.content.Context
import androidx.datastore.core.DataStoreFactory
import androidx.room.Room
import com.redkapetpty.shop2shopassesment.data.datastore.AuditPolicySerializer
import com.redkapetpty.shop2shopassesment.data.local.AppDb
import com.redkapetpty.shop2shopassesment.data.local.Converters
import com.redkapetpty.shop2shopassesment.data.remote.FakeInterceptor
import com.redkapetpty.shop2shopassesment.data.remote.TxApi
import com.redkapetpty.shop2shopassesment.data.repository.AuditPolicyRepositoryImpl
import com.redkapetpty.shop2shopassesment.data.repository.TransactionRepositoryImpl
import com.redkapetpty.shop2shopassesment.domain.repository.AuditPolicyRepository
import com.redkapetpty.shop2shopassesment.domain.repository.TransactionRepository
import com.redkapetpty.shop2shopassesment.domain.usecase.AddTransactionUseCase
import com.redkapetpty.shop2shopassesment.domain.usecase.DeleteTransactionUseCase
import com.redkapetpty.shop2shopassesment.domain.usecase.ObserveTransactionUseCase
import com.redkapetpty.shop2shopassesment.domain.usecase.UpdateAuditPolicyUseCase
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File

val appModule = module {

	//room
	single {
		Room.databaseBuilder(get(), AppDb::class.java, "tx.db")
			.addTypeConverter(Converters())
			.build()
	}
	single { get<AppDb>().txDao() }

	//retrofit
	single {
		Retrofit.Builder()
			.baseUrl("https://exampleurl.com/")
			.client(
				OkHttpClient.Builder()
					.addInterceptor(FakeInterceptor())
					.build())
			.addConverterFactory(MoshiConverterFactory.create())
			.build()
	}
	single { get<Retrofit>().create(TxApi::class.java) }

	//datastore
	single {
		DataStoreFactory.create(
			serializer = AuditPolicySerializer,
			produceFile = { File(get<Context>().filesDir, "audit_policy.pb") } )
	}

	//repository
	single<AuditPolicyRepository> { AuditPolicyRepositoryImpl(get()) }
	single<TransactionRepository> { TransactionRepositoryImpl(get(), get()) }

	//usecase
	factory { AddTransactionUseCase(get(), get()) }
	factory { DeleteTransactionUseCase(get()) }
	factory { ObserveTransactionUseCase(get()) }
	factory { UpdateAuditPolicyUseCase(get()) }

	//Viewmodel lets write some unit tests then our viewmodel
}