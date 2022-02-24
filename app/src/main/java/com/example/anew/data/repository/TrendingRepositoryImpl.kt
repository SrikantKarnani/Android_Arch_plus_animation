package com.example.anew.data.repository

import com.example.anew.data.model.GTrendingResults
import com.example.anew.domain.ApiService
import com.example.anew.domain.repos.TrendingRepository

class TrendingRepositoryImpl(private val apiService: ApiService) : TrendingRepository {
    override suspend fun fetchTrending(language: String?, pageNo: Int): GTrendingResults {
        return apiService.fetchTrending(queryParams = language ?: "language:Java", pageNo = pageNo)
    }
}