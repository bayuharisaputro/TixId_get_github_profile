package com.example.tixid_get_github_profile.datastore.getUserList

import com.example.tixid_get_github_profile.api.ApiInterface
import com.example.tixid_get_github_profile.model.UserListResponse
import com.example.tixid_get_github_profile.model.UserResponse
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before

import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Response
import java.io.IOException

class UserListRemoteDataStoreImplTest {
    @Mock
    val apiInterface : ApiInterface? = null

    @Mock
    val response : Response<ArrayList<UserListResponse?>>? = null

    @Mock
    val response2 : Response<UserResponse>? = null

    @Mock
    val callRetrofit : Call<ArrayList<UserListResponse?>>? = null

    @Mock
    val callRetrofit2 : Call<UserResponse>? = null

    @Before
    fun init(){
        MockitoAnnotations.openMocks(this)
        Mockito.`when`(apiInterface?.getGitHubUser(1000, 10)).thenReturn(callRetrofit)
        Mockito.`when`(apiInterface?.getGithubUserDetail("3001")).thenReturn(callRetrofit2)
    }

    @Test
    fun getUserList() {
        runBlocking {
            val getUserRes :ArrayList<UserListResponse?> = arrayListOf()
            Mockito.`when`(callRetrofit?.execute()).thenReturn(response)
            Mockito.`when`(response?.body()).thenReturn(getUserRes)
            Mockito.`when`(response?.isSuccessful).thenReturn(true)
            val remoteGetUser = UserListRemoteDataStoreImpl(apiInterface!!)
            val res = remoteGetUser.getUserList(1000)
            Truth.assertThat(res).isNotNull()
        }
    }

    @Test
    fun getUserDetail() {
        runBlocking {
            val getUserDetailRes = UserResponse(login = "testing", id = 3001)
            Mockito.`when`(callRetrofit2?.execute()).thenReturn(response2)
            Mockito.`when`(response2?.body()).thenReturn(getUserDetailRes)
            Mockito.`when`(response2?.isSuccessful).thenReturn(true)
            val remoteGetUser = UserListRemoteDataStoreImpl(apiInterface!!)
            val res = remoteGetUser.getUSerDetail("3001")
            Truth.assertThat(res).isNotNull()
            Truth.assertThat(res!!.login).isEqualTo("testing")
            Truth.assertThat(res.id).isEqualTo(3001)
        }
    }

    @Test
    fun getUserListError() {
        runBlocking {
            Mockito.`when`(callRetrofit?.execute()).thenThrow(IOException("No Internet"))
            val remoteGetUser = UserListRemoteDataStoreImpl(apiInterface!!)
            try{
                val res = remoteGetUser.getUserList(1000)
                throw RuntimeException("Is must not be here")
            }
            catch (e : Exception){
                //expected to throw error exception
            }
        }
    }

    @Test
    fun getUserDetailError() {
        runBlocking {
            Mockito.`when`(callRetrofit2?.execute()).thenThrow(IOException("No Internet"))
            val remoteGetUser = UserListRemoteDataStoreImpl(apiInterface!!)
            try{
                val res = remoteGetUser.getUSerDetail("3001")
                throw RuntimeException("Is must not be here")
            }
            catch (e : Exception){
                //expected to throw error exception
            }
        }
    }
}