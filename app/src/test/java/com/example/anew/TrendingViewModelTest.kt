package com.example.anew

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.anew.data.model.GTrendingResults
import com.example.anew.features.search.TrendingViewModel
import io.mockk.spyk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TrendingViewModelTest {
    @get:Rule
    val mockitoRule = MockitoJUnit.rule()
    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()
    lateinit var viewModel: TrendingViewModel

    @get:Rule
    public val instantExecutor = InstantTaskExecutorRule()
    val dispatcher = StandardTestDispatcher()

    @Before
    fun init() {
        Dispatchers.setMain(dispatcher)
        viewModel = TrendingViewModel(FakeTrendingRepoImpl())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Trending fetched from repository`() {
        val pr : Observer<Boolean> = spyk(Observer{})
        val mockObserver : Observer<GTrendingResults?> = spyk(Observer{})
        viewModel.fetchTrendingRepos(1, "d")
        viewModel.fetchedTrendingRepos.observeForever(mockObserver)
        viewModel.progressingLiveData.observeForever(pr)
        verify { pr.onChanged(true) }
        assert(viewModel.fetchedTrendingRepos.getOrAwaitValue()?.items?.isNullOrEmpty() == true)
        verify { pr.onChanged(false) }
    }
}