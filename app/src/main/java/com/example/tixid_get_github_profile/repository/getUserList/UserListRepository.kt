package com.example.tixid_get_github_profile.repository.getUserList

import com.example.tixid_get_github_profile.model.UserListResponse
import com.example.tixid_get_github_profile.model.UserResponse


interface UserListRepository {
    suspend fun getUserList(since: Int): ArrayList<UserListResponse?>?
    suspend fun getUserDetail(userId: String): UserResponse?
}