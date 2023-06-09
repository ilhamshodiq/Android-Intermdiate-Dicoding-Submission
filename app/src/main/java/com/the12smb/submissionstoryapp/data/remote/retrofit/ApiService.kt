package com.the12smb.submissionstoryapp.data.remote.retrofit

import com.the12smb.submissionstoryapp.data.remote.response.AddStoryResponse
import com.the12smb.submissionstoryapp.data.remote.response.DetailResponse
import com.the12smb.submissionstoryapp.data.remote.response.LoginResponse
import com.the12smb.submissionstoryapp.data.remote.response.RegisterResponse
import com.the12smb.submissionstoryapp.data.remote.response.StoriesResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

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
    suspend fun getAllStories( //suspend
        @Header("Authorization") token: String,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null
    ): StoriesResponse

    @GET("stories")
    fun getAllStoriesWithLocation(
        @Header("Authorization") token: String,
        @Query("location") location: Int = 0
    ): Call<StoriesResponse>

    @GET("stories/{id}")
    fun getDetailStory(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Call<DetailResponse>

    @POST("stories")
    @Multipart
    fun addStory(
        @Header("Authorization") token: String,
        @Part("description") description: RequestBody,
        @Part photo: MultipartBody.Part,
        @Part("lat") lat: Float? = null,
        @Part("lon") lon: Float? = null
    ): Call<AddStoryResponse>
}