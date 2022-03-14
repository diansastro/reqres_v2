package com.suitmedia.reqres_v2.di.builder

import com.suitmedia.reqres_v2.view.event.EventActivity
import com.suitmedia.reqres_v2.view.event.EventProvider
import com.suitmedia.reqres_v2.view.guest.GuestActivity
import com.suitmedia.reqres_v2.view.home.HomeActivity
import com.suitmedia.reqres_v2.view.user.UserActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract fun bindHomeActivity(): HomeActivity

    @ContributesAndroidInjector
    abstract fun bindUserActivity(): UserActivity

    @ContributesAndroidInjector(modules = [EventProvider::class])
    abstract fun bindEventActivity(): EventActivity

    @ContributesAndroidInjector
    abstract fun bindGuestActivity(): GuestActivity
}