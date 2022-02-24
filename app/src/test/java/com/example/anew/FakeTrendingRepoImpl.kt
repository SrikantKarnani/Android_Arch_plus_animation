package com.example.anew

import com.example.anew.data.model.GTrendingResults
import com.example.anew.domain.repos.TrendingRepository
import javax.inject.Inject

class FakeTrendingRepoImpl @Inject constructor() : TrendingRepository {
    override suspend fun fetchTrending(language: String?, pageNo: Int): GTrendingResults {
        print("Test")
        return GTrendingResults(false, emptyList(), 100)
    }
}