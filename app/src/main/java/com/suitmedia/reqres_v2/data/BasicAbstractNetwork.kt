package com.suitmedia.reqres_v2.data

import android.content.Context
import com.suitmedia.reqres_v2.data.interceptor.ContentTypeInterceptor
import okhttp3.OkHttpClient

abstract class BasicAbstractNetwork<T>(private val context: Context): AbstractNetwork<T>() {
    override fun okHttpClientBuilder(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        builder.addInterceptor(ContentTypeInterceptor())
        return super.okHttpClientBuilder(builder)
    }

    fun getNetworkService(): T {
        return networkService()
    }
}