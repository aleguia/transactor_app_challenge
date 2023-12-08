package com.leguia.manulifetransactor.feature_accounts.presentation

import com.leguia.manulifetransactor.core.util.Screen
import com.leguia.manulifetransactor.feature_accounts.domain.model.Account

/*
* Created by Fernando Leguia on December 07, 2023
*/

sealed class AccountScreenEvent {
//    object OnboardingDone : AccountScreenEvent()
//    object OnboardingNotDone : AccountScreenEvent()
    object OnQuitClicked : AccountScreenEvent()
//    object OnOpenAccounts : AccountScreenEvent()
    data class OnAccountClicked(val account: Account) : AccountScreenEvent()
    data class OnScreenChanged(val screen: Screen) : AccountScreenEvent()
}
data class AccountScreenState(
    val loading: Boolean = false,
    val accounts: List<Account> = emptyList(),
    val error: String? = null
)