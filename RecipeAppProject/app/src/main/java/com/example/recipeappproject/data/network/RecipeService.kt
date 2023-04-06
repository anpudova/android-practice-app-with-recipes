package com.example.recipeappproject.data.network

import androidx.viewbinding.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RecipeService {

    private const val APP_ID = "ba4b242d704c4412a2196c6c29f69183"
    private const val BASE_URL = "https://api.spoonacular.com/"

    private val okHttpClient: OkHttpClient by lazy {
        getOkHttpClient()
    }
    private val retrofitInstance: RecipeApiService by lazy {
        createRetrofitInstance()
    }


    @JvmName("getOkHttpClient1")
    private fun getOkHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val modifiedUrl = chain.request().url.newBuilder()
                    .addQueryParameter("apiKey", APP_ID)
                    .build()

                val request = chain.request().newBuilder().url(modifiedUrl).build()
                chain.proceed(request)
            }
        if (BuildConfig.DEBUG) {
            client.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }

        return client.build()
    }

    private fun createRetrofitInstance(): RecipeApiService {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofitBuilder.create(RecipeApiService::class.java)
    }

    fun getInstance(): RecipeApiService = retrofitInstance
}