package com.example.tixid_get_github_profile.dependencyInjection.module.userlist

import androidx.lifecycle.ViewModel
import com.example.tixid_get_github_profile.dependencyInjection.module.ViewModelKey
import com.example.tixid_get_github_profile.viewmodel.UserListViewModel
import com.example.tixid_get_github_profile.viewmodel.UserListViewModelImpl
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface UserListTaskModule {
    @Binds
    @IntoMap
    @ViewModelKey(UserListViewModelImpl::class)
    fun bindViewModelImpl(viewModel: UserListViewModelImpl): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserListViewModel::class)
    fun bindViewModel(viewModel: UserListViewModelImpl): ViewModel
}