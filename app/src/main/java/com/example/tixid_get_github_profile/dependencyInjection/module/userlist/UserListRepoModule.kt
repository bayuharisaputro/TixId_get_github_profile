package com.example.tixid_get_github_profile.dependencyInjection.module.userlist

import com.example.tixid_get_github_profile.repository.getUserList.UserListRepository
import com.example.tixid_get_github_profile.repository.getUserList.UserListRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface UserListRepoModule {

    @Binds
    fun bindsUserListRepository(repo: UserListRepositoryImpl): UserListRepository
}