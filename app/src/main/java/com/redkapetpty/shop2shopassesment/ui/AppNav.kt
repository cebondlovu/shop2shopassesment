package com.redkapetpty.shop2shopassesment.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.redkapetpty.shop2shopassesment.ui.screen.AuditPolicyScreen
import com.redkapetpty.shop2shopassesment.ui.screen.TransactionListScreen

@Composable
fun AppNav(start: String = "list", navController: NavHostController  = rememberNavController()) {
	NavHost(navController, startDestination = start) {
		composable("list") { TransactionListScreen(navController) }
		composable("settings") { AuditPolicyScreen(navController) }
	}
}