package com.leguia.manulifetransactor.feature_accounts.presentation.account_detail

import com.leguia.manulifetransactor.feature_accounts.domain.model.Activity
import com.leguia.manulifetransactor.feature_accounts.domain.model.TransactionsByDate


/*
* Created by Fernando Leguia on December 07, 2023
*/
data class AccountDetailScreenState(
    val loading: Boolean = false,
    val transactions: List<TransactionsByDate> = emptyList(),
    val error: String? = null
//    val isBottomSheetOpen: Boolean = false
)
