package com.suitmedia.reqres_v2.view.event

import com.suitmedia.reqres_v2.view.event.list.EventListFragment
import com.suitmedia.reqres_v2.view.event.map.EventMapFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class EventProvider {
    @ContributesAndroidInjector
    abstract fun provideEventList(): EventListFragment

    @ContributesAndroidInjector
    abstract fun provideEventMap(): EventMapFragment
}