package com.example.tixid_get_github_profile.datastore.getUserList

import com.example.tixid_get_github_profile.api.ApiInterface
import com.example.tixid_get_github_profile.exception.ApiResponseException
import com.example.tixid_get_github_profile.model.UserListResponse
import com.example.tixid_get_github_profile.model.UserResponse
import javax.inject.Inject
import kotlin.jvm.Throws


class UserListRemoteDataStoreImpl @Inject constructor(
        private val api: ApiInterface
) : UserListRemoteDataStore {


    @Throws(Exception::class)
    override suspend fun getUserList(since: Int): ArrayList<UserListResponse?>? {
        try {
            val apiRes = api.getGitHubUser(since = since).execute()
            try {
                if (apiRes.isSuccessful) {
                    return apiRes.body()!!
                } else {
                    throw ApiResponseException(apiRes.code().toString(), apiRes.message())
                }
            } catch (e: Exception) {
                throw e
            }
        } catch (e: java.lang.Exception) {
            throw e
        }
    }

    @Throws(Exception::class)
    override suspend fun getUSerDetail(userId: String): UserResponse? {
        try {
            val apiRes = api.getGithubUserDetail(userId = userId).execute()
            try {
                if (apiRes.isSuccessful) {
                    return apiRes.body()!!
                } else {
                    throw ApiResponseException(apiRes.code().toString(), apiRes.message())
                }
            } catch (e: Exception) {
                throw e
            }
        } catch (e: java.lang.Exception) {
            throw e
        }
    }
}