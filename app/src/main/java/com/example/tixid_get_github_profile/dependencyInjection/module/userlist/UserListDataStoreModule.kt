package com.example.tixid_get_github_profile.dependencyInjection.module.userlist

import com.example.tixid_get_github_profile.datastore.getUserList.UserListRemoteDataStore
import com.example.tixid_get_github_profile.datastore.getUserList.UserListRemoteDataStoreImpl
import dagger.Binds
import dagger.Module

@Module
interface UserListDataStoreModule {

    @Binds
    fun bindsUserListRemoteDataStore(remoteDataStore: UserListRemoteDataStoreImpl): UserListRemoteDataStore
}