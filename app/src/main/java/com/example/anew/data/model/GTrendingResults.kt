package com.example.anew.data.model

data class GTrendingResults(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)