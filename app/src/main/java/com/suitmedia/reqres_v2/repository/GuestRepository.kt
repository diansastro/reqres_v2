package com.suitmedia.reqres_v2.repository

import android.content.Context
import com.google.gson.Gson
import com.suitmedia.reqres_v2.base.AbstractPreferences
import com.suitmedia.reqres_v2.model.GuestData
import com.suitmedia.reqres_v2.objects.PrefKey
import com.suitmedia.reqres_v2.objects.PrefName
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class GuestRepository @Inject constructor(val context: Context, gson: Gson): AbstractPreferences(context, gson) {
    override fun getPreferencesGroup(): String = PrefName.GUEST_NAME

    var guestData = listOf<GuestData>()
        get() {
        if (null == field) field =
            getDataList(PrefKey.PREF_KEY_GUEST, GuestData::class.java)!!
        return field
    }
    set(value) {
        field = value
        if (null == value) clearData(PrefKey.PREF_KEY_GUEST)
        else saveDataList(PrefKey.PREF_KEY_GUEST, value)
    }
}