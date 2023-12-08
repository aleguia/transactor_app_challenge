package com.leguia.manulifetransactor.feature_onboarding.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leguia.manulifetransactor.feature_onboarding.domain.OnboardingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
* Created by Fernando Leguia on December 06, 2023
*/
@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val repository: OnboardingRepository
) : ViewModel() {

    fun onOpenAccounts() {
        viewModelScope.launch {
            repository.setOnboardingDone()
        }
    }

}