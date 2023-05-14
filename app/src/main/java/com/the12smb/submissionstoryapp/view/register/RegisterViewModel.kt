package com.the12smb.submissionstoryapp.view.register

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.the12smb.submissionstoryapp.data.remote.response.RegisterResponse
import com.the12smb.submissionstoryapp.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun register(
        name: String,
        email: String,
        password: String,
    ) : Boolean {
        _isLoading.value = true
        var errstatus = false
        val client = ApiConfig.getApiService().register(name, email, password)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                Log.e(TAG, response.body().toString())
                if (response.isSuccessful) {
                    _isLoading.value = false
                } else {
                    _isLoading.value = false
                    Log.e(TAG, "onFailureResponse: ${response.message()}")
                }
                errstatus = response.body()?.error.toString().toBoolean()
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailureThrowable: ${t.message}")
            }
        })
        return errstatus
    }

    companion object {
        private const val TAG = "RegisterViewModel"
    }
}