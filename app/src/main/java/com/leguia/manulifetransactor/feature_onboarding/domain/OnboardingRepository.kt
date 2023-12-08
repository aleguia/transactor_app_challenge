package com.leguia.manulifetransactor.feature_onboarding.domain

/*
* Created by Fernando Leguia on December 06, 2023
*/
interface OnboardingRepository {
    suspend fun setOnboardingDone()
}