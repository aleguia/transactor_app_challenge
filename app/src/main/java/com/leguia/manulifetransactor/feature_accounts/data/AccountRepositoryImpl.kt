package com.leguia.manulifetransactor.feature_accounts.data

import android.content.Context
import com.leguia.manulifetransactor.feature_accounts.domain.model.Account
import com.leguia.manulifetransactor.feature_accounts.domain.model.TransactionsByDate
import com.leguia.manulifetransactor.feature_accounts.domain.repository.AccountRepository
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import java.io.InputStream
import javax.inject.Inject

/*
* Created by Fernando Leguia on December 06, 2023
*/
class AccountRepositoryImpl @Inject constructor(
    private val context: Context
) : AccountRepository {

    override suspend fun quitSession() {
        context.getSharedPreferences("app", Context.MODE_PRIVATE)
            .edit()
            .putBoolean("onboarding", false)
            .apply()
    }

    override suspend fun getAccounts(): List<Account> {
        val fileAsString = readJSONFromAssetFile("listOfAccounts.json")
        delay(1000) //simulate a delay
        return Json.decodeFromString<List<Account>>(fileAsString)
    }



    override suspend fun getTransactionsForAccount(accountId: Int): List<TransactionsByDate> {

        val fileAsString = when(accountId){
            10 -> readJSONFromAssetFile("chequingAccount.json")
            12 -> readJSONFromAssetFile("savingsAccount.json")
            19 -> readJSONFromAssetFile("TfsaAccount.json")
            else -> throw Exception("Account not found")
        }
        return Json.decodeFromString<List<TransactionsByDate>>(fileAsString)
    }

    private fun readJSONFromAssetFile(fileName: String): String {

        val inputStream: InputStream = context.assets.open(fileName)
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        return String(buffer, Charsets.UTF_8)

    }

}