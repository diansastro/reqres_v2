package com.suitmedia.reqres_v2.data.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

open class ContentTypeInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(addHeader(chain.request()))
    }

    fun addHeader(oriRequest: Request): Request {
        return oriRequest.newBuilder().addHeader("Content-Type","application/json").build()
    }
}