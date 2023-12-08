package com.leguia.manulifetransactor.feature_accounts.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/*
* Created by Fernando Leguia on December 06, 2023
*/
@Serializable
data class Activity(
    @SerialName("balance")
    val balance: Double,
    @SerialName("date")
    val date: String,
    @SerialName("deposit_amount")
    val depositAmount: Double? = null,
    @SerialName("withdrawal_amount")
    val withdrawalAmount: Double? = null,
    @SerialName("description")
    val description: String,
    @SerialName("id")
    val id: Int,
    @SerialName("transaction_uid")
    val transactionUid: Long
)

fun Activity.formatForUI(): Activity {
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val formattedDate = LocalDate.parse(this.date, dateFormatter)
    val res = DateTimeFormatter.ofPattern("MMMM dd, yyyy").format(formattedDate)
    return this.copy(
        date = res
    )
}
