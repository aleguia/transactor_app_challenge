package com.leguia.manulifetransactor.core.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leguia.manulifetransactor.core.domain.ScreenRepository
import com.leguia.manulifetransactor.core.util.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
* Created by Fernando Leguia on December 07, 2023
*/
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ScreenRepository
): ViewModel() {

    var startDestination by mutableStateOf(Screen.OnboardingScreen.route)
        private set

    init {
        loadInitialScreen()
    }

    private fun loadInitialScreen() {
        viewModelScope.launch {
            startDestination = repository.getInitialScreenRoute()
        }
    }
}