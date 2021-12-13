package com.example.tixid_get_github_profile.api

import com.example.tixid_get_github_profile.model.UserListResponse
import com.example.tixid_get_github_profile.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("users")
    fun getGitHubUser(
        //now Github use API V3 and if we want to get pagination we used since
        //(which user ID the API should start listing users)
        @Query("since") since: Int,
        @Query("per_page") perPage: Int = 10
    ): Call<ArrayList<UserListResponse?>>

    @GET("user/{userId}")
    fun getGithubUserDetail(
        @Path("userId") userId: String
    ): Call<UserResponse>
}