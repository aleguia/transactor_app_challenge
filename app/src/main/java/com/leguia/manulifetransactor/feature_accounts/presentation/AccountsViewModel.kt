package com.leguia.manulifetransactor.feature_accounts.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leguia.manulifetransactor.feature_accounts.domain.model.Account
import com.leguia.manulifetransactor.feature_accounts.domain.model.formatForUI
import com.leguia.manulifetransactor.feature_accounts.domain.repository.AccountRepository
import com.leguia.manulifetransactor.feature_accounts.presentation.account_detail.AccountDetailScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
* Created by Fernando Leguia on December 06, 2023
*/
@HiltViewModel
class AccountsViewModel @Inject constructor(
    private val repository: AccountRepository
) : ViewModel(){

    var accountsState = mutableStateOf(AccountScreenState())
        private set

    var accountDetailScreenState = mutableStateOf(AccountDetailScreenState())
        private set

    private val accountSelected = MutableStateFlow<Account?>(null)

    init {
        loadAccounts()
        observeAccountSelected()
    }

    fun onQuit(){
        viewModelScope.launch {
            repository.quitSession()
        }
    }


    private fun loadAccounts(){
        accountsState.value = accountsState.value.copy(
            loading = true
        )

        viewModelScope.launch {
            try {
                val loadedAccounts = repository.getAccounts()
                accountsState.value = accountsState.value.copy(
                    loading = false,
                    accounts = loadedAccounts.formatForUI()
                )
            } catch (e: Exception){
                accountsState.value = accountsState.value.copy(
                    loading = false,
                    error = e.message
                )
            }
        }
    }

    private fun observeAccountSelected(){
        accountSelected
            .filterNotNull()
            .map { parseTransactionsForAccount(it) }
            .launchIn(viewModelScope)
    }

    private suspend fun parseTransactionsForAccount(account: Account){
        accountDetailScreenState.value = accountDetailScreenState.value.copy(
            loading = true
        )

        try{
            val transactions = repository.getTransactionsForAccount(account.id)

            accountDetailScreenState.value = accountDetailScreenState.value.copy(
                loading = false,
                transactions = transactions.formatForUI()
            )
        } catch (e: Exception){
            accountDetailScreenState.value = accountDetailScreenState.value.copy(
                loading = false,
                error = e.message
            )
        }
    }

    fun accountSelected(account: Account){
        accountDetailScreenState.value = accountDetailScreenState.value.copy(
            loading = true
        )
        accountSelected.value = account
    }

}