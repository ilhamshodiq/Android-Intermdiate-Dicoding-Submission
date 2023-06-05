package com.the12smb.submissionstoryapp.view.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.the12smb.submissionstoryapp.data.StoryRepository
import com.the12smb.submissionstoryapp.data.remote.response.ListStoryItem
import com.the12smb.submissionstoryapp.di.Injection
import kotlinx.coroutines.launch

class MainViewModel(
    private val storyRepository: StoryRepository
) : ViewModel() {
    fun getToken(): LiveData<String> = storyRepository.getToken()//ambil token
    fun getStory(token: String): LiveData<PagingData<ListStoryItem>> {
        return storyRepository.getStory(token).cachedIn(viewModelScope)
    }

    fun logout() {
        viewModelScope.launch {
            storyRepository.logout()
        }
    }

    companion object {
        const val TAG = "MainViewModel"
    }
}


class ViewModelFactoryMain(private val context: Context) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(Injection.provideRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}