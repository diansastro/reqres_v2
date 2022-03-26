package com.suitmedia.reqres_v2.data.entity

import android.content.Context
import com.suitmedia.reqres_v2.data.BasicAbstractNetwork
import com.suitmedia.reqres_v2.data.api.GuestApi
import com.suitmedia.reqres_v2.data.response.GuestResponse
import com.suitmedia.reqres_v2.extension.uiSubscribe
import com.suitmedia.reqres_v2.objects.NetworkCode
import com.suitmedia.reqres_v2.repository.GuestRepository
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import retrofit2.Response
import javax.inject.Inject

class GuestEntity @Inject constructor(context: Context, guestRepository: GuestRepository): BasicAbstractNetwork<GuestApi>(context, guestRepository) {
    override fun getApi(): Class<GuestApi> = GuestApi::class.java

    fun getGuestListPaging(page: Int): Observable<Response<GuestResponse>> = networkService().getGuestListPaging(page)

    fun getGuestList(): Observable<Response<GuestResponse>> = networkService().getGuestList()

    fun execOfflineGuest(onNext: (Response<GuestResponse>) -> Unit = {}, onError: (Throwable) -> Unit = {}, onComplete: () -> Unit = {}): Disposable {
        return getGuestList().uiSubscribe({
            if (it.code() == NetworkCode.CODE_OK) {
                it.body()?.data?.let { data ->
                    guestRepository.guestData = data
                }
            }
            onNext.invoke(it)
        }, onError, onComplete)
    }
}