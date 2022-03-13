package com.suitmedia.reqres_v2.data.entity

import android.content.Context
import com.suitmedia.reqres_v2.data.BasicAbstractNetwork
import com.suitmedia.reqres_v2.data.api.GuestApi
import com.suitmedia.reqres_v2.data.response.GuestResponse
import io.reactivex.Observable
import retrofit2.Response
import javax.inject.Inject

class GuestEntity @Inject constructor(context: Context): BasicAbstractNetwork<GuestApi>(context) {
    override fun getApi(): Class<GuestApi> = GuestApi::class.java

    fun getGuestList(): Observable<Response<GuestResponse>> = networkService().getGuestList()
}