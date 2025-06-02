package com.redkapetpty.shop2shopassesment.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.redkapetpty.shop2shopassesment.R
import com.redkapetpty.shop2shopassesment.core.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		startKoin {
			androidContext(this@MainActivity)
			modules(appModule)
		}
		setContent { AppNav() }
	}
}