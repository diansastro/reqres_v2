package com.suitmedia.reqres_v2.base

interface ErrorView {
    fun errorScreen(message: String? = "", code: Int? = -1)
    fun errorScreen(message: String? = "")
    fun forceLogout()
}