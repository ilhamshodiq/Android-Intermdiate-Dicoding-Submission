package com.the12smb.submissionstoryapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.the12smb.submissionstoryapp.data.database.StoryDatabase
import com.the12smb.submissionstoryapp.data.local.model.UserPreference
import com.the12smb.submissionstoryapp.data.remote.response.ListStoryItem
import com.the12smb.submissionstoryapp.data.remote.retrofit.ApiService

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class StoryRepository(
    private val storyDatabase: StoryDatabase,
    private val apiService: ApiService,
    context: Context
) {
    private val pref = UserPreference.getInstance(context.dataStore)

    fun getToken(): LiveData<String> = pref.getToken().asLiveData()//ambil token
    fun getName(): LiveData<String> = pref.getName().asLiveData()//ambil nama

    suspend fun logout() {
        pref.logout()
    }

    fun getStory(token: String): LiveData<PagingData<ListStoryItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(storyDatabase, apiService, token),
            pagingSourceFactory = { storyDatabase.storyDao().getAllStory() }
        ).liveData
    }

}