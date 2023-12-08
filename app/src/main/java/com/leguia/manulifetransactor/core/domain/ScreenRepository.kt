package com.leguia.manulifetransactor.core.domain

/*
* Created by Fernando Leguia on December 07, 2023
*/
interface ScreenRepository {
    suspend fun getInitialScreenRoute(): String
}