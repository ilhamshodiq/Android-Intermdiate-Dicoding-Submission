package com.the12smb.submissionstoryapp.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.the12smb.submissionstoryapp.data.remote.response.ListStoryItem
import com.the12smb.submissionstoryapp.data.remote.response.LoginResponse
import com.the12smb.submissionstoryapp.data.remote.response.LoginResult
import com.the12smb.submissionstoryapp.data.remote.response.StoriesResponse
import com.the12smb.submissionstoryapp.view.createFile
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream

object DataDummy {

    fun generateDummyStoriesResp(): StoriesResponse {
        val listStory = ArrayList<ListStoryItem>()
        for (i in 0..10) {
            val story = ListStoryItem(
                id = "$i",
                name = "Nama User $i",
                description = "lorem $i",
                photoUrl = "https://cdn.discordapp.com/attachments/852545958133628978/1112604681827520582/FB_IMG_1668927464168.jpg",
                createdAt = "2023-06-05T22:20:12.094Z",
                lat = -7.948833,
                lon = 112.60446
            )
            listStory.add(story)
        }
        return StoriesResponse(
            error = false,
            message = "Stories fetched successfully!!!!!!",
            listStory = listStory
        )
    }

    fun generateDummyAddStorySuccess(): LiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
        liveData.value = true
        return liveData
    }

    fun dummyFile(): File {
        val file = File("dummyFile")
        val data = "This is a dummy file."

        FileOutputStream(file).use { outputStream ->
            outputStream.write(data.toByteArray())
            outputStream.flush()
        }

        return file
    }
    fun description () = "ini deskripsii".toRequestBody("text/plain".toMediaTypeOrNull())
}