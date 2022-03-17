package com.suitmedia.reqres_v2.data.api

import com.suitmedia.reqres_v2.data.response.GuestResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GuestApi {

    @GET("users")
    fun getGuestListPaging(
        @Query("page") page: Int): Observable<Response<GuestResponse>>
}