package com.leguia.manulifetransactor.feature_accounts.presentation.account_detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leguia.manulifetransactor.feature_accounts.domain.model.Activity
import com.leguia.manulifetransactor.feature_accounts.domain.model.TransactionsByDate
import com.leguia.manulifetransactor.feature_accounts.domain.util.formatCurrency

/*
* Created by Fernando Leguia on December 07, 2023
*/
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AccountDetailScreen(
    transactions: List<TransactionsByDate>
) {
    Spacer(modifier = Modifier.height(16.dp))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Transactions",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            transactions.forEach { transaction ->
                stickyHeader {
                    TransactionsHeader(text = transaction.date)
                }
                items(transaction.activity) {
                    TransactionItem(transaction = it)
                }
            }
        }
    }
}

@Composable
fun TransactionsHeader(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary)
    )
}

@Composable
fun TransactionItem(
    transaction: Activity,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(
                1.dp,
                MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.9f),
                RoundedCornerShape(4.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = transaction.description,
                fontSize = 16.sp,
            )

            Text(
                text = transaction.transactionUid.toString(),
                fontSize = 12.sp,
            )
        }
        Column {
            Text(
                text = transaction.depositAmount?.formatCurrency() ?:
                        transaction.withdrawalAmount?.formatCurrency() ?:
                        0.0.formatCurrency(),
                color = transaction.withdrawalAmount?.let{ Color.Red } ?: MaterialTheme.colorScheme.onSecondary,
                fontSize = 16.sp,
            )

            Text(
                text = transaction.balance.formatCurrency(),
                fontStyle = FontStyle.Italic,
                fontSize = 12.sp,
            )
        }
    }


}