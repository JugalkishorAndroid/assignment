package com.example.nasapictureapp.core.api

import android.content.Context
import com.example.nasapictureapp.util.MockResponseFileReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.mockito.Mockito
import retrofit2.Retrofit
import retrofit2.create
import java.util.concurrent.TimeUnit


@ExperimentalCoroutinesApi
abstract class BaseApiTest {
    private val context: Context = Mockito.mock(Context::class.java)

    private lateinit var mockWebServer: MockWebServer

    protected val url: String
        get() = mockWebServer.url("/").toString()

    /* @Rule
     var mockitoRule = MockitoJUnit.rule()*/

    @get:Rule
    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)


    @Before
    open fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @After
    open fun tearDown() {
        Dispatchers.resetMain()
    }

    fun mockHttpResponse( res: String, responseCode: Int) =
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(responseCode)
                .setBody(MockResponseFileReader(res).content)
        )


    inline fun <reified T> createApi(url: String): T {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                    .callTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS).build()
            ).build().create()
    }
}