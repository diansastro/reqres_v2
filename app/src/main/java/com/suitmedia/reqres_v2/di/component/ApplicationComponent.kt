package com.suitmedia.reqres_v2.di.component

import android.app.Application
import com.suitmedia.reqres_v2.MainActivity
import com.suitmedia.reqres_v2.di.builder.ActivityBuilder
import com.suitmedia.reqres_v2.di.module.ApplicationModule
import com.suitmedia.reqres_v2.di.module.NetModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, NetModule::class, ActivityBuilder::class, AndroidInjectionModule::class])

interface ApplicationComponent {
    fun Inject(mainActivity: MainActivity)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}