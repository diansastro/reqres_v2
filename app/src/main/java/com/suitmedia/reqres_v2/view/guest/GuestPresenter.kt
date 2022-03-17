package com.suitmedia.reqres_v2.view.guest

import com.suitmedia.reqres_v2.base.BasePresenter
import com.suitmedia.reqres_v2.data.entity.GuestEntity
import com.suitmedia.reqres_v2.extension.uiSubscribe
import com.suitmedia.reqres_v2.objects.NetworkCode
import javax.inject.Inject

class GuestPresenter @Inject constructor(val guestEntity: GuestEntity): BasePresenter<GuestContract.View>(), GuestContract.Presenter {
    override fun execGuestListPaging(page: Int) {
        addSubscription(guestEntity.getGuestListPaging(page).uiSubscribe({
            when(it.code()) {
                NetworkCode.CODE_OK -> view?.getGuestListPaging(it.body())
                else -> {
                    view?.errorScreen("Unable to Load Data")
                }
            }
        }, {
           view?.errorScreen(it.localizedMessage)
        }, {}))
    }
}