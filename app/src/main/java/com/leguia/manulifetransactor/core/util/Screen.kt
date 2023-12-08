package com.leguia.manulifetransactor.core.util

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/*
* Created by Fernando Leguia on December 06, 2023
*/
sealed class Screen(val route: String) {
    object OnboardingScreen: Screen("onboarding_screen")
    object AccountsScreen: Screen("accounts_screen")
}

@Composable
fun FullLoader() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.6f))
            .clickable { },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.wrapContentSize(),
            strokeWidth = 4.dp
        )
    }
}

@Composable
fun InfoDialog(
    openDialog: Boolean,
    title: String,
    description: String,
    onConfirm: () -> Unit
) {
    if (openDialog) {
        AlertDialog(
            onDismissRequest = onConfirm,
            title = {
                Text(text = title)
            },
            text = {
                Text(text = description)
            },
            confirmButton = {
                TextButton(
                    onClick = onConfirm
                ) {
                    Text("Ok")
                }
            }
        )
    }
}

