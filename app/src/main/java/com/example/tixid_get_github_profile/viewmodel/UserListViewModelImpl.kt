package com.example.tixid_get_github_profile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tixid_get_github_profile.model.UserListResponse
import com.example.tixid_get_github_profile.model.UserResponse
import com.example.tixid_get_github_profile.repository.getUserList.UserListRepository
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.ArrayList

class UserListViewModelImpl @Inject constructor(
        private val repository: UserListRepository
) : UserListViewModel() {

    private val mIsLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    private val mIsLoadMore: MutableLiveData<Boolean> = MutableLiveData()
    private val mIsLoadGetDetail: MutableLiveData<Boolean> = MutableLiveData()
    private val mErrorHandler: MutableLiveData<Exception> = MutableLiveData()
    private val mUserList: MutableLiveData<ArrayList<UserListResponse?>> = MutableLiveData()
    private val mUserGetDetail: MutableLiveData<UserResponse?> = MutableLiveData()

    override val ldIsLoading: LiveData<Boolean> = mIsLoading
    override val ldIsLoadingMore: LiveData<Boolean> = mIsLoadMore
    override val ldIsLoadingGetDetail: LiveData<Boolean> = mIsLoadGetDetail
    override val ldErrorHandler: LiveData<Exception> = mErrorHandler
    override val ldUserList: LiveData<ArrayList<UserListResponse?>> = mUserList
    override val ldUserDetail: LiveData<UserResponse?> = mUserGetDetail

    private var wholeRawData: ArrayList<UserListResponse?> = arrayListOf()


    override fun requestGetUserList(since: Int) {
        viewModelScope.launch {
            mIsLoading.value = true
            try {
                wholeRawData = repository.getUserList(since)?: arrayListOf()
                mUserList.value = wholeRawData
                mErrorHandler.value = null
            }
            catch (e: Exception) {
                mUserList.value = arrayListOf()
                mErrorHandler.value = e
            }
            finally {
                mIsLoading.value = false
            }
        }
    }

    override fun requestLoadMoreUserList(since: Int) {
        viewModelScope.launch {
            mIsLoadMore.value = true
            try {
                wholeRawData.addAll(wholeRawData.lastIndex, repository.getUserList(since)?: arrayListOf())
                mUserList.value = wholeRawData
                mErrorHandler.value = null
            }
            catch (e: Exception) {
                mErrorHandler.value = e
            }
            finally {
                mIsLoadMore.value = false
            }
        }
    }

    override fun requestGetUserDetail(userId: String) {
        viewModelScope.launch {
            mIsLoadGetDetail.value = true
            try {
                val res = repository.getUserDetail(userId)
                mUserGetDetail.value = res
                mErrorHandler.value = null
            }
            catch (e: Exception) {
                mUserGetDetail.value = null
                mErrorHandler.value = e
            }
            finally {
                mIsLoadGetDetail.value = false
            }
        }
    }
}