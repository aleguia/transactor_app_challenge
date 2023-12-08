package com.leguia.manulifetransactor.feature_accounts.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/*
* Created by Fernando Leguia on December 06, 2023
*/
@Serializable
data class Account(
    @SerialName("account_number")
    val accountNumber: String,
    @SerialName("balance")
    val balance: Double,
    @SerialName("display_name")
    val displayName: String,
    @SerialName("id")
    val id: Int
)

fun Account.formatForUI(): Account = this.copy(
    displayName = this.displayName.split(" ").first()
)

fun List<Account>.formatForUI(): List<Account> = this.map { it.formatForUI() }



