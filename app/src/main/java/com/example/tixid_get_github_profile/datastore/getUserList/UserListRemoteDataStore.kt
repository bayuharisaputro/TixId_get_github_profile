package com.example.tixid_get_github_profile.datastore.getUserList

import com.example.tixid_get_github_profile.model.UserListResponse
import com.example.tixid_get_github_profile.model.UserResponse

interface UserListRemoteDataStore {
    suspend fun getUserList(since: Int): ArrayList<UserListResponse?>?
    suspend fun getUSerDetail(userId: String): UserResponse?
}