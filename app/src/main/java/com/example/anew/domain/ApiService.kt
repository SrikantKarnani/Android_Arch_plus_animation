package com.example.anew.domain

import com.example.anew.data.model.GTrendingResults
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/search/repositories")
    suspend fun fetchTrending(
        @Query("q") queryParams: String,
        @Query("sort") sort: String = "stars",
        @Query("order") sortingOrder: String = "desc",
        @Query("per_page") perPage: Int = 20,
        @Query("page") pageNo: Int
    ): GTrendingResults
}