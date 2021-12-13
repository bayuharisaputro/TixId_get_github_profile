package com.example.tixid_get_github_profile.repository.getUserList

import com.example.tixid_get_github_profile.datastore.getUserList.UserListRemoteDataStoreImpl
import com.example.tixid_get_github_profile.exception.NoInternetException
import com.example.tixid_get_github_profile.model.UserListResponse
import com.example.tixid_get_github_profile.model.UserResponse
import com.example.tixid_get_github_profile.util.ConnectionUtil
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class UserListRepositoryImplTest {
    @Mock
    var remoteDataStoreImpl : UserListRemoteDataStoreImpl? = null
    @Mock
    var netUtil : ConnectionUtil? = null

    @Before
    fun init(){
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun getUserList() {
        runBlocking {
            val getUserRes :ArrayList<UserListResponse?> = arrayListOf()
            Mockito.`when`(remoteDataStoreImpl?.getUserList(1000)).thenReturn(getUserRes)
            Mockito.`when`(netUtil?.isInternetConnected()).thenReturn(true)
            val getUserRepository : UserListRepositoryImpl? = UserListRepositoryImpl(remoteDataStoreImpl!!, netUtil!!)

            val res = getUserRepository?.getUserList(1000)
            Truth.assertThat(res).isNotNull()
        }
    }


    @Test
    fun getUserListError() {
        runBlocking {
            val getUserRes :ArrayList<UserListResponse?> = arrayListOf()
            Mockito.`when`(remoteDataStoreImpl?.getUserList(1000)).thenReturn(getUserRes)
            Mockito.`when`(netUtil?.isInternetConnected()).thenReturn(false)
            val getUserRepository : UserListRepositoryImpl? = UserListRepositoryImpl(remoteDataStoreImpl!!, netUtil!!)
            try {
                val res = getUserRepository?.getUserList(1000)
                throw RuntimeException("Is must not be here")
            }
            catch (e : NoInternetException) {
                //expected to throw No Internet Exception
            }
        }
    }

    @Test
    fun getUserDetail() {
        runBlocking {
            val getUserDetailRes = UserResponse(login = "testing", id = 3001)
            Mockito.`when`(remoteDataStoreImpl?.getUSerDetail("3001")).thenReturn(getUserDetailRes)
            Mockito.`when`(netUtil?.isInternetConnected()).thenReturn(true)
            val getUserRepository : UserListRepositoryImpl? = UserListRepositoryImpl(remoteDataStoreImpl!!, netUtil!!)

            val res = getUserRepository?.getUserDetail("3001")
            Truth.assertThat(res).isNotNull()
            Truth.assertThat(res!!.login).isEqualTo("testing")
            Truth.assertThat(res.id).isEqualTo(3001)
        }
    }

    @Test
    fun getUserDetailError() {
        runBlocking {
            val getUserDetailRes = UserResponse(login = "testing", id = 3001)
            Mockito.`when`(remoteDataStoreImpl?.getUSerDetail("3001")).thenReturn(getUserDetailRes)
            Mockito.`when`(netUtil?.isInternetConnected()).thenReturn(false)
            val getUserRepository : UserListRepositoryImpl? = UserListRepositoryImpl(remoteDataStoreImpl!!, netUtil!!)
            try {
                val res = getUserRepository?.getUserDetail("3001")
                throw RuntimeException("Is must not be here")
            }
            catch (e : NoInternetException) {
                //expected to throw No Internet Exception
            }
        }
    }
}