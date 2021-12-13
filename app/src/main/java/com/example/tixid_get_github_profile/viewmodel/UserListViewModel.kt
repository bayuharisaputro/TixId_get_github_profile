package com.example.tixid_get_github_profile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.tixid_get_github_profile.model.UserListResponse
import com.example.tixid_get_github_profile.model.UserResponse

abstract class UserListViewModel : ViewModel() {

    abstract val ldIsLoading: LiveData<Boolean>
    abstract val ldIsLoadingMore: LiveData<Boolean>
    abstract val ldIsLoadingGetDetail: LiveData<Boolean>

    abstract val ldErrorHandler: LiveData<Exception>

    abstract val ldUserList: LiveData<ArrayList<UserListResponse?>>
    abstract val ldUserDetail: LiveData<UserResponse?>

    abstract fun requestGetUserList(since:Int)
    abstract fun requestLoadMoreUserList(since:Int)
    abstract fun requestGetUserDetail(userId:String)

}