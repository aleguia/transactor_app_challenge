package com.leguia.manulifetransactor.feature_accounts.domain.repository

import com.leguia.manulifetransactor.feature_accounts.domain.model.Account
import com.leguia.manulifetransactor.feature_accounts.domain.model.TransactionsByDate

/*
* Created by Fernando Leguia on December 06, 2023
*/
interface AccountRepository {
    suspend fun getAccounts(): List<Account>

    suspend fun getTransactionsForAccount(accountId: Int): List<TransactionsByDate>

    suspend fun quitSession()
}