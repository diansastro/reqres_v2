package com.suitmedia.reqres_v2.view.guest

import com.suitmedia.reqres_v2.base.ErrorView
import com.suitmedia.reqres_v2.data.response.GuestResponse

interface GuestContract {
    interface View: ErrorView {
        fun getGuestList(guestResponse: GuestResponse?)
    }

    interface Presenter {
        fun execGuestList()
    }
}