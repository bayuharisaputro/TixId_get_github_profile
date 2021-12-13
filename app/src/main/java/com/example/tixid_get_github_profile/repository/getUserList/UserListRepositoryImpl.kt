package com.example.tixid_get_github_profile.repository.getUserList

import com.example.tixid_get_github_profile.datastore.getUserList.UserListRemoteDataStore
import com.example.tixid_get_github_profile.exception.NoInternetException
import com.example.tixid_get_github_profile.model.UserListResponse
import com.example.tixid_get_github_profile.model.UserResponse
import com.example.tixid_get_github_profile.util.ConnectionUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.jvm.Throws

class UserListRepositoryImpl @Inject constructor(
        private val remoteDataStore: UserListRemoteDataStore,
        private val networkUtil: ConnectionUtil
) : UserListRepository {


    @Throws(Exception::class)
    override suspend fun getUserList(since: Int): ArrayList<UserListResponse?>? = withContext(Dispatchers.IO){
        if (networkUtil.isInternetConnected()) {
            return@withContext remoteDataStore.getUserList(since)
        }
        else {
            throw NoInternetException()
        }
    }

    @Throws(Exception::class)
    override suspend fun getUserDetail(userId: String): UserResponse? = withContext(Dispatchers.IO) {
        if (networkUtil.isInternetConnected()) {
            return@withContext remoteDataStore.getUSerDetail(userId)
        }
        else {
            throw NoInternetException()
        }
    }
}