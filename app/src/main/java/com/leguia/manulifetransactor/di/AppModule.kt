package com.leguia.manulifetransactor.di

import android.content.Context
import com.leguia.manulifetransactor.core.data.ScreenRepositoryImpl
import com.leguia.manulifetransactor.core.domain.ScreenRepository
import com.leguia.manulifetransactor.feature_accounts.data.AccountRepositoryImpl
import com.leguia.manulifetransactor.feature_accounts.domain.repository.AccountRepository
import com.leguia.manulifetransactor.feature_onboarding.data.OnboardingRepositoryImpl
import com.leguia.manulifetransactor.feature_onboarding.domain.OnboardingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*
* Created by Fernando Leguia on December 06, 2023
*/
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideScreenRepository(@ApplicationContext appContext: Context): ScreenRepository {
        return ScreenRepositoryImpl(appContext)
    }

    @Singleton
    @Provides
    fun provideOnboardingRepository(@ApplicationContext appContext: Context): OnboardingRepository {
        return OnboardingRepositoryImpl(appContext)
    }

    @Singleton
    @Provides
    fun provideAccountsRepository(@ApplicationContext appContext: Context): AccountRepository {
        return AccountRepositoryImpl(appContext)
    }
}