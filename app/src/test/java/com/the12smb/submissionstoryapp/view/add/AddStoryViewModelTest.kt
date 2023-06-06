package com.the12smb.submissionstoryapp.view.add

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.the12smb.submissionstoryapp.data.StoryRepository
import com.the12smb.submissionstoryapp.data.local.model.UserPreference
import com.the12smb.submissionstoryapp.utils.DataDummy
import com.the12smb.submissionstoryapp.utils.MainDispatcherRule
import com.the12smb.submissionstoryapp.utils.getOrAwaitValue
import com.the12smb.submissionstoryapp.view.main.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AddStoryViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var addStoryViewModel: AddStoryViewModel
    private val imageFile = DataDummy.dummyFile()
    private val description = DataDummy.description()
    private val token = "Token blalba"
    private val lat = -7.948833
    private val lon = 112.60446

    @Test
    fun `When AddStory should return true when upload is successful`() {
        val dummySuccess = DataDummy.generateDummyAddStorySuccess()
        val expectedResponse = MutableLiveData<Boolean>()
        expectedResponse.value = dummySuccess.value
        `when`(addStoryViewModel.addStory(token, description.toString(), imageFile, lat.toFloat(), lon.toFloat())).thenReturn(expectedResponse)
        val actualData = addStoryViewModel.addStory(token, description.toString(), imageFile, lat.toFloat(), lon.toFloat()).getOrAwaitValue()
        assertEquals(expectedResponse.value, actualData)
    }



}