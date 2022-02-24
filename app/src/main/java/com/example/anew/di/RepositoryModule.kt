package com.example.anew.di

import com.example.anew.data.repository.TrendingRepositoryImpl
import com.example.anew.domain.ApiService
import com.example.anew.domain.repos.TrendingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideTrendingRepo(
        apiService: ApiService
    ): TrendingRepository {
        return TrendingRepositoryImpl(apiService)
    }
}