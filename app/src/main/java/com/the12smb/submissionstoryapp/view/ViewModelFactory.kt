package com.the12smb.submissionstoryapp.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.the12smb.submissionstoryapp.data.local.model.UserPreference
import com.the12smb.submissionstoryapp.view.add.AddStoryViewModel
import com.the12smb.submissionstoryapp.view.detail.DetailViewModel
import com.the12smb.submissionstoryapp.view.login.LoginViewModel
import com.the12smb.submissionstoryapp.view.main.MainViewModel
import com.the12smb.submissionstoryapp.view.maps.MapsViewModel

class ViewModelFactory(private val pref: UserPreference) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MapsViewModel::class.java) -> {
                MapsViewModel(pref) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(pref) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(pref) as T
            }
            modelClass.isAssignableFrom(AddStoryViewModel::class.java) -> {
                AddStoryViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}