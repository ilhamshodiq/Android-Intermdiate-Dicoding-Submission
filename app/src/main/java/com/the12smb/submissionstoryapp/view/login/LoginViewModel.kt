package com.the12smb.submissionstoryapp.view.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.the12smb.submissionstoryapp.data.local.model.UserModel
import com.the12smb.submissionstoryapp.data.local.model.UserPreference
import com.the12smb.submissionstoryapp.data.remote.response.LoginResponse
import com.the12smb.submissionstoryapp.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val pref: UserPreference) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _login = MutableLiveData<UserModel>()
    val login: LiveData<UserModel> = _login
    fun getToken(): LiveData<String> = pref.getToken().asLiveData()//ambil token
    fun isLogin(): LiveData<Boolean> = pref.isLogin().asLiveData()

    private var _loginResp = MutableLiveData<Response<LoginResponse>?>()
    val loginResp: LiveData<Response<LoginResponse>?> get() = _loginResp

    fun login(email: String, password: String){
        _loginResp.value = null
        _isLoading.value = true
        val client = ApiConfig.getApiService().login(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val name = response.body()?.loginResult?.name.toString()
                    val userId = response.body()?.loginResult?.userId.toString()
                    val token = response.body()?.loginResult?.token.toString()
                    _login.value = UserModel(name, userId, token)
                    viewModelScope.launch {
                        login.value?.let { pref.saveUser(it) }
                    }
                } else {
                    _isLoading.value = false
                    Log.e(TAG, "onFailureResponse: ${response.message()}")
                }
                _loginResp.postValue(response)
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailureThrowable: ${t.message}")
            }
        })
    }


    companion object {
        const val TAG = "LoginViewModel"
    }
}
