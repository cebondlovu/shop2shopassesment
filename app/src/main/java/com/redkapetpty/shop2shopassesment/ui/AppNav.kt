package com.redkapetpty.shop2shopassesment.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.redkapetpty.shop2shopassesment.ui.screen.AuditPolicyScreen
import com.redkapetpty.shop2shopassesment.ui.screen.TransactionListScreen

@Composable
fun AppNav(start: String = "list", navController: NavController = rememberNavController()) {
	NavHost(navController, startDestination = start) {
		composable("list") { TransactionListScreen(navController) }
		composable("settings") { AuditPolicyScreen(navController) }
	}
}