package com.example.anew.domain.repos

import com.example.anew.data.model.GTrendingResults

interface TrendingRepository {
    suspend fun fetchTrending(language: String?, pageNo : Int): GTrendingResults
}