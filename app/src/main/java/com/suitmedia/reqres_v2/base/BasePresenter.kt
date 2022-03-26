package com.suitmedia.reqres_v2.base

import com.suitmedia.reqres_v2.repository.GuestRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

open class BasePresenter<T: ErrorView> {

    @Inject
    lateinit var guestRepository: GuestRepository

    var compose: CompositeDisposable = CompositeDisposable()

    var view:T ?= null

    open fun detachView(){
        this.view = null
        compose.clear()
    }

    fun addSubscription(disposable: Disposable) = compose.add(disposable)

    fun clearAllSubscription() = compose.clear()
}