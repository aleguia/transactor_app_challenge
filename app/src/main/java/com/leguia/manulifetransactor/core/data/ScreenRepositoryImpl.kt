package com.leguia.manulifetransactor.core.data

import android.content.Context
import com.leguia.manulifetransactor.core.domain.ScreenRepository
import com.leguia.manulifetransactor.core.util.Screen
import javax.inject.Inject

/*
* Created by Fernando Leguia on December 07, 2023
*/
class ScreenRepositoryImpl @Inject constructor(
    private val context: Context
): ScreenRepository {
    override suspend fun getInitialScreenRoute(): String {
        val isDone = context.getSharedPreferences("app", Context.MODE_PRIVATE)
            .getBoolean("onboarding", false)
        return if(isDone) Screen.AccountsScreen.route else Screen.OnboardingScreen.route
    }
}