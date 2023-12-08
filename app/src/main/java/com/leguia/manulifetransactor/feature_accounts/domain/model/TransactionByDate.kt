package com.leguia.manulifetransactor.feature_accounts.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/*
* Created by Fernando Leguia on December 06, 2023
*/
@Serializable
data class TransactionsByDate(
    @SerialName("activity")
    val activity: List<Activity>,
    @SerialName("date")
    val date: String
)

fun TransactionsByDate.formatForUI(): TransactionsByDate {
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val formattedDate = LocalDate.parse(this.date, dateFormatter)
    val res = DateTimeFormatter.ofPattern("MMMM dd, yyyy").format(formattedDate)
    return this.copy(
        date = res
    )
}

fun List<TransactionsByDate>.formatForUI(): List<TransactionsByDate> {
    return this.map {
        it.formatForUI()
    }
}



