package com.suitmedia.reqres_v2.data

import com.suitmedia.reqres_v2.BuildConfig
import com.suitmedia.reqres_v2.data.base.BaseNetwork
import com.suitmedia.reqres_v2.data.interceptor.ContentTypeInterceptor
import okhttp3.OkHttpClient

abstract class AbstractNetwork<T>(): BaseNetwork<T>() {
    override fun getBaseUrl(): String = BuildConfig.BASE_URL

    override fun okHttpClientBuilder(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        builder.addInterceptor(ContentTypeInterceptor())
        return super.okHttpClientBuilder(builder)
    }
}