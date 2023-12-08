package com.leguia.manulifetransactor.feature_accounts.presentation

import com.leguia.manulifetransactor.feature_accounts.domain.model.Account
import com.leguia.manulifetransactor.feature_accounts.domain.model.Activity
import com.leguia.manulifetransactor.feature_accounts.domain.model.TransactionsByDate
import com.leguia.manulifetransactor.feature_accounts.domain.repository.AccountRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/*
* Created by Fernando Leguia on December 07, 2023
*/
@OptIn(ExperimentalCoroutinesApi::class)
class AccountsViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private val repository = mockk<AccountRepository>()
    private lateinit var viewModel: AccountsViewModel


    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = AccountsViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `When accounts are loading, the state is loading`() {
        Assert.assertEquals(viewModel.accountsState.value.loading, true)
        dispatcher.scheduler.advanceUntilIdle()
        Assert.assertEquals(viewModel.accountsState.value.loading, false)
    }

    @Test
    fun `When error is thrown, the state is error`() {
        Assert.assertEquals(viewModel.accountsState.value.loading, true)
        coEvery { repository.getAccounts() } throws Exception("Something bad happened")
        dispatcher.scheduler.advanceUntilIdle()
        Assert.assertEquals(viewModel.accountsState.value.loading, false)
        Assert.assertEquals(viewModel.accountsState.value.error, "Something bad happened")
    }

    @Test
    fun `When accounts are loaded, account state match`() {
        Assert.assertEquals(viewModel.accountsState.value.loading, true)
        coEvery { repository.getAccounts() } returns listOf<Account>(
            Account(
                accountNumber = "123456789",
                displayName = "Chequing account",
                balance = 1000.0,
                id = 10
            )
        )
        dispatcher.scheduler.advanceUntilIdle()
        Assert.assertEquals(viewModel.accountsState.value.loading, false)
        assert(viewModel.accountsState.value.accounts.isNotEmpty())
    }

    @Test
    fun `When account selected, detailed state should match`(){

        Assert.assertEquals(viewModel.accountsState.value.loading, true)
        coEvery { repository.getAccounts() } returns listOf<Account>(
            Account(
                accountNumber = "123456789",
                displayName = "Chequing account",
                balance = 1000.0,
                id = 10
            )
        )

        coEvery { repository.getTransactionsForAccount(any()) } returns listOf(
            TransactionsByDate(
                date = "2021-01-01",
                activity = listOf(
                    Activity(
                        balance = 1000.0,
                        date = "2021-01-01",
                        depositAmount = 1000.0,
                        withdrawalAmount = null,
                        description = "Deposit",
                        id = 1,
                        transactionUid = 1
                    )
                )
            )
        )

        dispatcher.scheduler.advanceUntilIdle()
        Assert.assertEquals(viewModel.accountsState.value.loading, false)
        assert(viewModel.accountsState.value.accounts.isNotEmpty())

        viewModel.accountSelected(Account(
            accountNumber = "123456789",
            displayName = "Chequing account",
            balance = 1000.0,
            id = 10
        ))

        dispatcher.scheduler.advanceUntilIdle()
        Assert.assertEquals(viewModel.accountDetailScreenState.value.loading, false)
        assert(viewModel.accountDetailScreenState.value.transactions.isNotEmpty())
    }


}