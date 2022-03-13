package com.suitmedia.reqres_v2.di.module

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetModule {
    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()
}