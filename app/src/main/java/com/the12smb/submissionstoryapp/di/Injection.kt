package com.the12smb.submissionstoryapp.di

import android.content.Context
import com.the12smb.submissionstoryapp.data.remote.retrofit.ApiConfig
import com.the12smb.submissionstoryapp.data.StoryRepository
import com.the12smb.submissionstoryapp.data.database.StoryDatabase

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val database = StoryDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return StoryRepository(database, apiService, context)
    }
}