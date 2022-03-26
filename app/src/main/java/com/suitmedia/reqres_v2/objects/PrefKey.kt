package com.suitmedia.reqres_v2.objects

import androidx.annotation.StringDef

@StringDef
annotation class PrefKey {
    companion object {
        const val PREF_KEY_GUEST = "p_guest"
    }
}
