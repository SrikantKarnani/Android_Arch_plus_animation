package com.example.anew.features.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anew.data.model.GTrendingResults
import com.example.anew.data.model.Item
import com.example.anew.domain.repos.TrendingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.SocketException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class TrendingViewModel @Inject constructor(private val repo: TrendingRepository) : ViewModel() {
    private val trendingRepos = MutableLiveData<GTrendingResults?>()
    val fetchedTrendingRepos: LiveData<GTrendingResults?> = trendingRepos
    private var fetchedList: List<Item> = emptyList()
    private val progressing = MutableLiveData<Boolean>()
    val progressingLiveData: LiveData<Boolean> = progressing

    private val error = MutableLiveData<String?>()
    val errorLiveData: LiveData<String?> = error

    fun fetchTrendingRepos(pageNo: Int,language: String?=null) {
        viewModelScope.launch {
            progressing.postValue(true)
            try {
                val fetchedResponse = repo.fetchTrending(language,pageNo)
                trendingRepos.postValue(fetchedResponse)
                error.postValue(null)
                fetchedList = fetchedResponse.items
                progressing.postValue(false)
            } catch (e: Exception) {
                progressing.postValue(false)
                trendingRepos.postValue(null)
                if (e is UnknownHostException) {
                    error.postValue("Your Internet seems to be having some issues. Please check it !!")
                } else {
                    error.postValue("Some error occurred")
                }
            }
        }
    }

    fun filterList(keyword: String?): List<Item> {
        return keyword?.takeIf { it.isNotBlank() }?.let { key ->
            fetchedList.filter {
                it.name.contains(key, true) || it.description.contains(
                    key,
                    true
                )
            }
        } ?: fetchedList
    }
}