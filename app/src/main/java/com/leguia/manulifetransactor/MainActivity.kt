package com.leguia.manulifetransactor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.leguia.manulifetransactor.core.presentation.MainViewModel
import com.leguia.manulifetransactor.core.util.Screen
import com.leguia.manulifetransactor.feature_accounts.presentation.AccountsScreen
import com.leguia.manulifetransactor.feature_accounts.presentation.AccountsViewModel
import com.leguia.manulifetransactor.feature_onboarding.presentation.OnboardingScreen
import com.leguia.manulifetransactor.feature_onboarding.presentation.OnboardingViewModel
import com.leguia.manulifetransactor.ui.theme.ManulifeTransactorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ManulifeTransactorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    App(viewModel.startDestination)
                }
            }
        }
    }
}

@Composable
fun App( startRoute: String ) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startRoute
    ) {
        composable(route = Screen.OnboardingScreen.route) {
            val onboardingViewModel = hiltViewModel<OnboardingViewModel>()
            val onOpenAccounts = remember {
                {
                    onboardingViewModel.onOpenAccounts()
                    navController.navigate(Screen.AccountsScreen.route){
                        popUpTo(startRoute) { inclusive = true }
                    }
                }
            }
            OnboardingScreen(onOpenAccounts)
        }

        composable(route = Screen.AccountsScreen.route) {
            val accountsViewModel = hiltViewModel<AccountsViewModel>()
            val onQuit = remember {
                {
                    accountsViewModel.onQuit()
                    navController.popBackStack()
                    navController.navigate(Screen.OnboardingScreen.route)
                }
            }
            AccountsScreen(
                accountsState = accountsViewModel.accountsState.value,
                detailScreenState = accountsViewModel.accountDetailScreenState.value,
                onAccountClicked = accountsViewModel::accountSelected,
                onQuitClicked = onQuit
            )
        }
    }
}
//    @Preview(showBackground = true)
//    @Composable
//    fun GreetingPreview() {
//        ManulifeTransactorTheme {
//            Greeting("Android")
//        }
//    }