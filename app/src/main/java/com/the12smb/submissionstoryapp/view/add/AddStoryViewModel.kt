package com.the12smb.submissionstoryapp.view.add

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.the12smb.submissionstoryapp.data.local.model.UserPreference
import com.the12smb.submissionstoryapp.data.remote.response.AddStoryResponse
import com.the12smb.submissionstoryapp.data.remote.retrofit.ApiConfig
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AddStoryViewModel (private val pref: UserPreference) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getToken() : LiveData<String> = pref.getToken().asLiveData()//ambil token


     fun addStory(token: String, desc: String, photo: File, lat: Float? = null, lon: Float? = null) :LiveData<Boolean> {
        _isLoading.value = true
         val uploadImageRequest = MutableLiveData<Boolean>() // untuk check apakah upload berhasil

        val description = desc.toRequestBody("text/plain".toMediaType())
        val requestImageFile = photo.asRequestBody("image/jpeg".toMediaType())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo",
            photo.name,
            requestImageFile
        )

        val client = ApiConfig.getApiService().addStory(token, description, imageMultipart, lat, lon)
        client.enqueue(object : Callback<AddStoryResponse> {
            override fun onResponse(
                call: Call<AddStoryResponse>,
                response: Response<AddStoryResponse>
            ) {
                if (response.isSuccessful) {
                    uploadImageRequest.value = response.isSuccessful
                    _isLoading.value = false
                } else {
                    _isLoading.value = false
                    Log.e(TAG, "onFailureResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<AddStoryResponse>, t: Throwable) {
                _isLoading.value = false
                uploadImageRequest.value = false
                Log.e(TAG, "onFailureThrowable: ${t.message}")
            }

        })
        return uploadImageRequest
    }


    companion object {
        private const val TAG = "AddStoryViewModel"
    }
}