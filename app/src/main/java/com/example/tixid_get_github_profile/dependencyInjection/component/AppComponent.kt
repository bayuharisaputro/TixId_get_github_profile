package com.example.tixid_get_github_profile.dependencyInjection.component

import android.app.Application
import androidx.multidex.MultiDexApplication
import com.example.tixid_get_github_profile.dependencyInjection.module.NetworkModule
import com.example.tixid_get_github_profile.dependencyInjection.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelModule::class, NetworkModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: BaseApp): AppComponent
    }
    fun userListTaskComponent(): UserListTaskComponent.Factory

}

class BaseApp: MultiDexApplication() {
    val appComponent = DaggerAppComponent.factory().create(this)
}