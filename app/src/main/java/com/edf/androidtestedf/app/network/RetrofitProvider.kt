package com.edf.androidtestedf.app.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

object RetrofitProvider {

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private val httpClient = OkHttpClient.Builder()
        .readTimeout(5, TimeUnit.SECONDS)
        .connectTimeout(5, TimeUnit.SECONDS)

    private fun getRetrofit(baseUrl: String) : Retrofit{
        val httpclient = httpClient.build()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpclient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun provideRetrofit(baseUrl: String = BASE_URL): Retrofit {
        return getRetrofit(baseUrl)
    }
}