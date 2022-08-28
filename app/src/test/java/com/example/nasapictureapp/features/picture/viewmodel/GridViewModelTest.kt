package com.example.nasapictureapp.features.picture.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.nasapictureapp.core.viewmodel.BaseViewModelTest
import com.example.nasapictureapp.features.picture.data.repository.PictureFakeRepository
import com.example.nasapictureapp.utl.MainCoroutineScopeRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.net.ssl.HttpsURLConnection

class GridViewModelTest: BaseViewModelTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = MainCoroutineScopeRule()

    private lateinit var viewModel: GridViewModel

    private fun createViewModel() =
        GridViewModel(PictureFakeRepository())

    @Before
    fun setup() {
        viewModel = createViewModel()
    }


    @Test
    fun `call picture data return success`() = runBlocking(Dispatchers.IO){
     //   mockHttpResponse("demo.json", HttpsURLConnection.HTTP_OK)
        viewModel.getPictureResponse()
        delay(100)
        Assert.assertEquals(viewModel.imageSources.value?.size,26)
    }

}