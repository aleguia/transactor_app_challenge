package com.leguia.manulifetransactor.feature_onboarding.data

import android.content.Context
import com.leguia.manulifetransactor.feature_onboarding.domain.OnboardingRepository
import javax.inject.Inject

/*
* Created by Fernando Leguia on December 06, 2023
*/
class OnboardingRepositoryImpl @Inject constructor(
    private val context: Context
): OnboardingRepository {


    override suspend fun setOnboardingDone() {
        context.getSharedPreferences("app", Context.MODE_PRIVATE)
            .edit()
            .putBoolean("onboarding", true)
            .apply()
    }
}