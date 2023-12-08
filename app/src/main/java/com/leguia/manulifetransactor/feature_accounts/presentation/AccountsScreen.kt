package com.leguia.manulifetransactor.feature_accounts.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leguia.manulifetransactor.core.util.FullLoader
import com.leguia.manulifetransactor.core.util.InfoDialog
import com.leguia.manulifetransactor.feature_accounts.domain.model.Account
import com.leguia.manulifetransactor.feature_accounts.domain.util.formatCurrency
import com.leguia.manulifetransactor.feature_accounts.presentation.account_detail.AccountDetailScreen
import com.leguia.manulifetransactor.feature_accounts.presentation.account_detail.AccountDetailScreenState
import kotlinx.coroutines.launch

/*
* Created by Fernando Leguia on December 06, 2023
*/

@Composable
fun AccountsContent(
    padding: PaddingValues,
    state: AccountScreenState,
    onAccountClicked: (Account) -> Unit,
) {
        if (state.loading) {
            FullLoader()
        } else if(!state.error.isNullOrBlank()) {
            InfoDialog(
                openDialog = true,
                title = "Error",
                description = state.error,
                onConfirm = {}
            )
        } else {
            LoadedContent(padding, state.accounts, onAccountClicked)
        }

}


@Composable
fun LoadedContent(
    padding: PaddingValues,
    accounts: List<Account>,
    onAccountClicked: (Account) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .then(Modifier.padding(16.dp)),
        horizontalAlignment = Alignment.Start
    ) {

        Text(
            text = "Accounts",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        AccountList(
            accounts = accounts,
            onAccountClicked = onAccountClicked
        )
    }

}

@Composable
fun AccountsTobBar(
    onQuitClicked: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Transactor",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(16.dp)
                    .weight(3f)
            )
            Button(
                modifier = Modifier
                    .weight(1f),
                onClick = onQuitClicked
            ) {
                Text(
                    text = "Quit",
                    fontSize = 14.sp,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountsScreen(
    accountsState: AccountScreenState,
    detailScreenState: AccountDetailScreenState,
    onAccountClicked: (Account) -> Unit,
    onQuitClicked: () -> Unit
) {
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = false,
        )
    )
    val scope = rememberCoroutineScope()
    /* Use this to control the BottomSheet's visibility
    for example in Button(onClick = {}) or LaunchedEffect{}
    val scope = rememberCoroutineScope()
    scope.launch {
        scaffoldState.bottomSheetState.show()
        scaffoldState.bottomSheetState.hide()
        scaffoldState.bottomSheetState.expand()
        scaffoldState.bottomSheetState.partialExpand()
    }
     */

    BottomSheetScaffold(
        modifier = Modifier,
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp, // BottomSheet's height when it's collapsed
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContainerColor = MaterialTheme.colorScheme.primaryContainer,
        sheetContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        sheetShadowElevation = 4.dp,
        sheetDragHandle = null,
        sheetSwipeEnabled = true,
        topBar = { AccountsTobBar(onQuitClicked) },
        sheetContent = {
            AccountDetailScreen(
                transactions = detailScreenState.transactions
            )
        }
    ) { innerPadding ->
        AccountsContent(
            padding = innerPadding,
            state = accountsState,
            onAccountClicked = { account ->
                scope.launch {
                    onAccountClicked(account)
                    scaffoldState.bottomSheetState.expand()
                }
            }
        )
    }
}

@Composable
fun AccountList(accounts: List<Account>, onAccountClicked: (Account) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(accounts) {
            AccountRow(account = it, onClick = onAccountClicked)
        }
    }
}


@Composable
fun AccountRow(account: Account, onClick: (Account) -> Unit) {

    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        ),
        modifier = Modifier.clickable {
            onClick(account)
        }
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Column {
                    Text(
                        text = account.displayName,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = account.accountNumber,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
                Row {
                    Text(
                        text = account.balance.formatCurrency(),
                        color = if (account.balance > 0) MaterialTheme.colorScheme.onSecondary else Color.Red,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowRight,
                        contentDescription = "Go to account detail",
                    )
                }
            }
        }
    }

}


