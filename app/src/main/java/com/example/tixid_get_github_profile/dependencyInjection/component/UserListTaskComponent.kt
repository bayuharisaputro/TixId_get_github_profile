package com.example.tixid_get_github_profile.dependencyInjection.component
import com.example.tixid_get_github_profile.MainActivity
import com.example.tixid_get_github_profile.dependencyInjection.module.userlist.UserListDataStoreModule
import com.example.tixid_get_github_profile.dependencyInjection.module.userlist.UserListRepoModule
import com.example.tixid_get_github_profile.dependencyInjection.module.userlist.UserListTaskModule
import dagger.Subcomponent

@Subcomponent(
        modules = [UserListDataStoreModule::class,
            UserListRepoModule::class,
            UserListTaskModule::class
        ]
)
interface UserListTaskComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): UserListTaskComponent
    }

    fun inject(activity: MainActivity)
}