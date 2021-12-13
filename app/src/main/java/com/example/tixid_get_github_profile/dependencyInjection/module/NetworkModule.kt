package com.example.tixid_get_github_profile.dependencyInjection.module

import com.example.tixid_get_github_profile.api.ApiClient
import com.example.tixid_get_github_profile.api.ApiInterface
import dagger.Module
import dagger.Provides

@Module
object NetworkModule {

    @Provides
    @JvmStatic
    fun provideApiInterfaceSync(): ApiInterface {
        return ApiClient.client.create(ApiInterface::class.java)
    }
}