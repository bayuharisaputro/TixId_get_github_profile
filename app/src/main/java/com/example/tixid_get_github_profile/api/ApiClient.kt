package com.example.tixid_get_github_profile.api

import com.example.tixid_get_github_profile.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private var retrofit: Retrofit? = null

    const val base_url = "https://api.github.com/"
    const val token = "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"
    val client: Retrofit
        get() {
            if (retrofit == null) {
                val httpClient = OkHttpClient().newBuilder()
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(90, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS)

                if (BuildConfig.DEBUG) {
                    val interceptor = HttpLoggingInterceptor()
                    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                    httpClient.addInterceptor(interceptor)
                    httpClient.addInterceptor { it ->
                        val request = it.request().newBuilder().addHeader("Authorization", "Bearer $token").build()
                        it.proceed(request)
                    }
                }

                val okHttpClient = httpClient.build()
                retrofit = Retrofit.Builder()
                    .baseUrl(base_url)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!
        }
}