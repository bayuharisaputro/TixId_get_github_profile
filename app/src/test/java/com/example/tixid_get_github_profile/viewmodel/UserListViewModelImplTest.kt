package com.example.tixid_get_github_profile.viewmodel

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tixid_get_github_profile.model.UserListResponse
import com.example.tixid_get_github_profile.model.UserResponse
import com.example.tixid_get_github_profile.repository.getUserList.UserListRepositoryImpl
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.annotation.Config
import java.io.IOException


@Config(maxSdk = Build.VERSION_CODES.P)
@RunWith(AndroidJUnit4::class)
class UserListViewModelImplTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Mock
    var repo : UserListRepositoryImpl? = null

    @Before
    fun init(){
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun requestGetUserList() {
        runBlocking {
            val getUserRes :ArrayList<UserListResponse?> = arrayListOf()
            val vm = UserListViewModelImpl(repo!!)
            Mockito.`when`(repo?.getUserList(1000)).thenReturn(getUserRes)

            vm.requestGetUserList(1000)

            val userList = vm.ldUserList.value
            val error = vm.ldErrorHandler.value
            val isLoading = vm.ldIsLoading.value
            Truth.assertThat(userList).isNotNull()
            Truth.assertThat(error).isNull()
            Truth.assertThat(isLoading).isFalse()
        }
    }

    @Test
    fun requestGetUserListError(){
        runBlocking {
            val vm = UserListViewModelImpl(repo!!)
            Mockito.`when`(repo?.getUserList(1000)).thenThrow(IOException("error"))

            vm.requestGetUserList(1000)

            val userList = vm.ldUserList.value
            val error = vm.ldErrorHandler.value
            val isLoading = vm.ldIsLoading.value
            Truth.assertThat(userList).isEmpty()
            Truth.assertThat(error).isNotNull()
            Truth.assertThat(error?.message).isEqualTo("error")
            Truth.assertThat(isLoading).isFalse()
        }
    }

    @Test
    fun requestGetUserDetail() {
        runBlocking {
            val getUserDetailRes = UserResponse(login = "testing", id = 3001)
            val vm = UserListViewModelImpl(repo!!)
            Mockito.`when`(repo?.getUserDetail("3001")).thenReturn(getUserDetailRes)

            vm.requestGetUserDetail("3001")

            val userDetail = vm.ldUserDetail.value
            val error = vm.ldErrorHandler.value
            val isLoading = vm.ldIsLoadingGetDetail.value
            Truth.assertThat(userDetail).isNotNull()
            Truth.assertThat(error).isNull()
            Truth.assertThat(isLoading).isFalse()
        }
    }

    @Test
    fun requestGetUserDetailError(){
        runBlocking {
            val vm = UserListViewModelImpl(repo!!)
            Mockito.`when`(repo?.getUserDetail("3001")).thenThrow(IOException("error"))

            vm.requestGetUserDetail("3001")

            val userDetail = vm.ldUserDetail.value
            val error = vm.ldErrorHandler.value
            val isLoading = vm.ldIsLoadingGetDetail.value
            Truth.assertThat(userDetail).isNull()
            Truth.assertThat(error).isNotNull()
            Truth.assertThat(error?.message).isEqualTo("error")
            Truth.assertThat(isLoading).isFalse()
        }
    }
}