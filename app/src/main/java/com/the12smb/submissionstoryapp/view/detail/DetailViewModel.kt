package com.the12smb.submissionstoryapp.view.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.the12smb.submissionstoryapp.data.local.model.UserPreference
import com.the12smb.submissionstoryapp.data.remote.response.DetailResponse
import com.the12smb.submissionstoryapp.data.remote.response.Story
import com.the12smb.submissionstoryapp.data.remote.retrofit.ApiConfig
import com.the12smb.submissionstoryapp.view.main.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val pref: UserPreference) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getToken(): LiveData<String> = pref.getToken().asLiveData()//ambil token

    private val _detail = MutableLiveData<Story>()
    val detail: LiveData<Story> = _detail

    fun getDetail(id: String, token: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailStory(id, token)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detail.value = response.body()?.story
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(MainViewModel.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }


    companion object {
        private const val TAG = "DetailViewModel"
    }
}