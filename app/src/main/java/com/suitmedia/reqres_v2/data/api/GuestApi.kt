package com.suitmedia.reqres_v2.data.api

import com.suitmedia.reqres_v2.data.response.GuestResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

interface GuestApi {

    @GET("users")
    fun getGuestList(): Observable<Response<GuestResponse>>
}