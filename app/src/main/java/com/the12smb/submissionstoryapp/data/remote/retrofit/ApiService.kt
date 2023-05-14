package com.the12smb.submissionstoryapp.data.remote.retrofit

import com.the12smb.submissionstoryapp.data.remote.response.LoginResponse
import com.the12smb.submissionstoryapp.data.remote.response.RegisterResponse
import com.the12smb.submissionstoryapp.data.remote.response.StoriesResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @GET("stories")
    fun getAllStories(
        @Header("Authorization") token: String
    ): Call<StoriesResponse>
}